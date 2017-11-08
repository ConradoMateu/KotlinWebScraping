import di.kdi
import domain.Product
import tornadofx.Controller
import tornadofx.observable
import usecases.*

class MainActivityPresenter : Controller() {

    private val getAllProducts: GetProducts by kdi()
    private val getAllBrands: GetBrands by kdi()
    private val addProcessedProducts: AddProcessedProducts by kdi()
    private val amazonSearchProduct: AmazonSearchProduct by kdi()
    private val fnacSearchProduct: FnacSearchProduct by kdi()
    val articles = mutableListOf("Todos").observable()
    val brands = mutableListOf<String>().observable()

    init {
        articles.addAll(getAllProducts())
        brands.addAll(getAllBrands())
    }

    fun searchItems(article: String, brands: List<String>, stores: MutableMap<String, Boolean>, pages: Int = 1, keepResults: Boolean = false) {
        val selectedStores = stores.filter { (_, value) -> value }.map { (key, _) -> key }
        val products = mutableListOf<Product>()

        if (selectedStores.contains("https://www.amazon.es")) {
            if (brands.isNotEmpty()) {
                val amazonProducts = brands
                    .map { brand: String ->
                        amazonSearchProduct(if (article == articles[0]) "Cafetera" else article, brand, pages)
                    }.flatten()
                addProcessedProducts(amazonProducts)
                products.addAll(amazonProducts)
            } else {
                val amazonProducts = amazonSearchProduct(if (article == articles[0]) "Cafetera" else article, page = pages)
                addProcessedProducts(amazonProducts)
                products.addAll(amazonProducts)
            }
        }

        if (selectedStores.contains("https://www.fnac.es")) {
            if (brands.isNotEmpty()) {
                val fnacProducts = brands
                        .map { brand: String ->
                            fnacSearchProduct(if (article == articles[0]) "Cafetera" else article, brand, pages)
                        }.flatten()
                addProcessedProducts(fnacProducts)
                products.addAll(fnacProducts)
            } else {
                val fnacProducts = fnacSearchProduct(if (article == articles[0]) "Cafetera" else article, page = pages)
                addProcessedProducts(fnacProducts)
                products.addAll(fnacProducts)
            }
        }

        if (keepResults) {
            ResultsActivity.navigateWithPreviousResults()
        } else {
            ResultsActivity.navigateWithResults(products)
        }

    }

}