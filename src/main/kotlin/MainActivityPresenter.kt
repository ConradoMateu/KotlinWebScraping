import di.kdi
import domain.Product
import kotlinx.coroutines.experimental.Deferred
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
    private val searchProducts: AmazonSearchProduct by kdi()
    val articles = mutableListOf("Todos").observable()
    val brands = mutableListOf<String>().observable()

    init {
        articles.addAll(getAllProducts())
        brands.addAll(getAllBrands())
    }

    fun searchItems(article: String, brands: List<String>, stores: MutableMap<String, Boolean>, pages: Int = 1, keepResults: Boolean = false) {
        launch(JavaFx) {
            val selectedStores = stores.filter { (_, value) -> value }.map { (key, _) -> key }
            var products: List<Product>

            if (selectedStores.contains("https://www.amazon.es")) {
                if (brands.isNotEmpty()) {
                    products = brands
                            .map { brand: String ->
                                async { searchProducts(if (article == articles[0]) "Cafetera" else article, brand, pages) }
                            }
                            .map { it.await() }
                            .flatten()
                    addProcessedProducts(products)
                } else {
                    products = async { searchProducts(if (article == articles[0]) "Cafetera" else article, page = pages) }.await()
                    addProcessedProducts(products)
                }

                if (keepResults) {
                    ResultsActivity.navigateWithPreviousResults()
                } else {
                    ResultsActivity.navigateWithResults(products)
                }
            }
        }
    }

}