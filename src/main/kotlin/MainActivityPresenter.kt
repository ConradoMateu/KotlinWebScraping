import di.kdi
import domain.Product
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
        val selectedStores = stores.filter { (_, value) -> value }.map { (key, _) -> key }
        var products: List<Product>

        if (selectedStores.contains("https://www.amazon.es")) {
            if (brands.isNotEmpty()) {
                products = brands
                    .map { brand: String ->
                        searchProducts(if (article == articles[0]) "Cafetera" else article, brand, pages)
                    }.flatten()
                addProcessedProducts(products)
            } else {
                products = searchProducts(if (article == articles[0]) "Cafetera" else article, page = pages)
                addProcessedProducts(products)
            }

            if (keepResults) {
                ResultsActivity.navigateWithPreviousResults()
            } else {
                ResultsActivity.navigateWithResults(products)
            }
        }

        if (selectedStores.contains("https://www.elcorteingles.es")) {
            if (brands.isNotEmpty()) {
                products = brands
                        .map { brand: String ->
                            searchProducts(if (article == articles[0]) "Cafetera" else article, brand, pages)
                        }.flatten()
                addProcessedProducts(products)
            } else {
                products = searchProducts(if (article == articles[0]) "Cafetera" else article, page = pages)
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