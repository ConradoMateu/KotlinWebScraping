import di.kdi
import domain.Product
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.javafx.JavaFx
import kotlinx.coroutines.experimental.launch
import tornadofx.Controller
import tornadofx.observable
import usecases.*

class MainActivityPresenter : Controller() {

    private val getAllProducts: GetProducts by kdi()
    private val getAllBrands: GetBrands by kdi()
    private val addProcessedProducts: AddProcessedProducts by kdi()
    private val amazonSearchProduct: AmazonSearchProduct by kdi()
    private val fnacSearchProduct: FnacSearchProduct by kdi()
    private val corteInglesSearchProduct: ElCorteInglesSearchProduct by kdi()

    val articles = mutableListOf("Todos").observable()
    val brands = mutableListOf<String>().observable()

    init {
        articles.addAll(getAllProducts())
        brands.addAll(getAllBrands())
    }

    fun searchItems(article: String, brands: List<String>, stores: MutableMap<String, Boolean>, pages: Int = 1) {
        val selectedStores = stores.filter { (_, value) -> value }.map { (key, _) -> key }
        val products = mutableListOf<Product>()

        launch(JavaFx) {
            if (brands.isNotEmpty()) {
                selectedStores.forEach { store ->
                    brands.map { brand ->
                        async { searchItemsForStore(store, brand, article, pages, products) }
                    }.map { it.await() }
                }
            } else {
                selectedStores.forEach {
                    async { searchItemsForStore(it, null, article, pages, products) }.await()
                }
            }

            ResultsActivity.navigateWithPreviousResults()

        }

    }

    private fun searchItemsForStore(storeUrl: String, brand: String?, article: String, pages: Int, products: MutableCollection<Product>) {
        val productName = if (article == articles[0]) "Cafetera" else article
        val result = when (storeUrl) {
            CONSTANTS.FNAC.URL -> fnacSearchProduct(productName, brand, pages)
            CONSTANTS.AMAZON.URL -> amazonSearchProduct(productName, brand, pages)
            else -> emptyList()
        }
        addProcessedProducts(result)
        products.addAll(result)
    }


}