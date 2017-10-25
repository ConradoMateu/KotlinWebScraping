import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import datasource.BrandDAO
import datasource.ProductDAO
import tornadofx.App
import tornadofx.launch
import usecases.GetBrands
import usecases.GetProducts

class Application : App(MainActivity::class) {
    companion object {
        val kodein = Kodein {
            bind<BrandDAO>() with singleton { BrandDAO() }
            bind<ProductDAO>() with singleton { ProductDAO() }

            bind<GetBrands>() with provider { GetBrands() }
            bind<GetProducts>() with provider { GetProducts() }
        }
    }
}

fun main(args: Array<String>) = launch<Application>(args)


