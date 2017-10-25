import di.kdi
import tornadofx.Controller
import tornadofx.observable
import usecases.GetBrands
import usecases.GetProducts

class MainActivityPresenter : Controller() {

    private val getAllProducts: GetProducts by kdi()
    private val getAllBrands: GetBrands by kdi()

    val articles = mutableListOf("Todos").observable()
    val brands = mutableListOf<String>().observable()

    init {
        articles.addAll(getAllProducts.execute())
        brands.addAll(getAllBrands.execute())
    }

    fun searchItems(article: String, brands: List<String>) {
        val brandsAux = brands.joinToString()
        if (article == articles[0]) {
            println("Se va a buscar Cafeteras de las marcas $brandsAux")
        } else {
            println("Se va a buscar $article de las marcas $brandsAux")
        }
    }

}