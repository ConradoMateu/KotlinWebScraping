import com.github.salomonbrys.kodein.*
import datasource.BrandDAO
import datasource.ProductDAO
import datasource.Stores.AmazonRepository
import datasource.Stores.CorteInglesRepository
import datasource.Stores.StoreRepository
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import tornadofx.App
import tornadofx.launch
import usecases.GetBrands
import usecases.GetProducts
import usecases.SearchProducts

class Application : App(MainActivity::class) {
    companion object {
        val kodein = Kodein {
            bind<BrandDAO>() with singleton { BrandDAO() }
            bind<ProductDAO>() with singleton { ProductDAO() }

            bind<StoreRepository>("https://www.amazon.es") with provider { AmazonRepository(ChromeDriver()) }
            bind<StoreRepository>("https://www.elcorteingles.es") with provider { CorteInglesRepository(ChromeDriver()) }

            bind<GetBrands>() with provider { GetBrands() }
            bind<GetProducts>() with provider { GetProducts() }
        }
    }

    init {
        System.setProperty(CONSTANTS.CHROME.TYPE, CONSTANTS.CHROME.PATH)
    }
}

fun main(args: Array<String>) = launch<Application>(args)


