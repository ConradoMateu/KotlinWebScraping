import com.github.salomonbrys.kodein.*
import datasource.BrandDAO
import datasource.ProductDAO
import datasource.Stores.AmazonRepository
import datasource.Stores.FnacRepository
import datasource.Stores.StoreRepository
import org.apache.commons.io.FileUtils.copyURLToFile
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import tornadofx.App
import tornadofx.launch
import usecases.*
import java.io.File

class Application : App(MainActivity::class) {
    companion object {
        val kodein = Kodein {
            bind<BrandDAO>() with singleton { BrandDAO() }
            bind<ProductDAO>() with singleton { ProductDAO() }
            bind<WebDriver>() with singleton { ChromeDriver() }

            bind<StoreRepository>("https://www.amazon.es") with provider { AmazonRepository() }
            bind<StoreRepository>("https://www.fnac.es") with provider { FnacRepository() }

            bind<GetBrands>() with provider { GetBrands() }
            bind<GetProducts>() with provider { GetProducts() }
            bind<GetProcessedProducts>() with provider { GetProcessedProducts() }
            bind<AddProcessedProducts>() with provider { AddProcessedProducts() }
            bind<AmazonSearchProduct>() with provider { AmazonSearchProduct() }
            bind<FnacSearchProduct>() with provider { FnacSearchProduct() }
        }
    }

    init {
        val f = File("bin")
        if (!f.exists()) {
            f.mkdirs()
        }
        val chromeDriver = File("bin${File.separator}chromedriver${CONSTANTS.getOSExtension()}")
        if (!chromeDriver.exists()) {
            chromeDriver.createNewFile()
            copyURLToFile(CONSTANTS.CHROME.PATH, chromeDriver)
        }
        System.setProperty(CONSTANTS.CHROME.TYPE, chromeDriver.absolutePath)
    }
}

fun main(args: Array<String>) = launch<Application>(args)


