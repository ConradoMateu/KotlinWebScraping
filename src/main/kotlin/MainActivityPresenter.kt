import com.github.salomonbrys.kodein.factory
import di.kdi
import tornadofx.Controller
import tornadofx.observable
import usecases.GetBrands
import usecases.GetProducts
import usecases.AmazonSearchProduct

class MainActivityPresenter : Controller() {

    private val getAllProducts: GetProducts by kdi()
    private val getAllBrands: GetBrands by kdi()
    private val searchProducts: AmazonSearchProduct by kdi()

    val articles = mutableListOf("Todos").observable()
    val brands = mutableListOf<String>().observable()

    init {
        articles.addAll(getAllProducts())
        brands.addAll(getAllBrands())
    }

    fun searchItems(article: String, brands: List<String>, stores: MutableMap<String, Boolean>) {
        val selectedStores = stores.filter { (_, value) -> value }.map { (key, _) -> key }

        if (selectedStores.contains("https://www.amazon.es")) {
            searchProducts(if (article == articles[0]) "Cafetera" else article)
        }
    }

}