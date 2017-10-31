import di.kdi
import tornadofx.Controller
import tornadofx.observable
import usecases.GetBrands
import usecases.GetProducts
import usecases.SearchProducts

class MainActivityPresenter : Controller() {

    private val getAllProducts: GetProducts by kdi()
    private val getAllBrands: GetBrands by kdi()

    val articles = mutableListOf("Todos").observable()
    val brands = mutableListOf<String>().observable()

    init {
        articles.addAll(getAllProducts.execute())
        brands.addAll(getAllBrands.execute())
    }

    fun searchItems(article: String, brands: List<String>, stores: MutableMap<String, Boolean>) {
        val brandsAux = brands.joinToString()
        val selectedStores = stores.filter { (_, value) -> value }.map { (key, _) -> key }
        if (article == articles[0]) {
            stores.keys.forEach {
                SearchProducts(it).execute()
            }
        } else {
            stores.keys.forEach {
                SearchProducts(it).execute()
            }
        }
    }

}