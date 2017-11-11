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
    private val corteInglesProduct: ElCorteInglesSearchProduct by kdi()
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
            searchItemsForStore(amazonSearchProduct, brands, article, pages, products)
        }

        if (selectedStores.contains("https://www.fnac.es")) {
            searchItemsForStore(fnacSearchProduct, brands, article, pages, products)
        }

        if (selectedStores.contains(CONSTANTS.CORTEINGLES.URL)){
            searchItemsForStore(corteInglesProduct, brands, article, pages, products)
        }

        if (keepResults) {
            ResultsActivity.navigateWithPreviousResults()
        } else {
            ResultsActivity.navigateWithResults(products)
        }

    }

    private fun searchItemsForStore(storeUseCase: ISearchProducts, brands: List<String>, article: String, pages: Int, products: MutableList<Product>) {
        if (brands.isNotEmpty()) {
            val storeProducts = brands
                    .map { brand: String ->
                        storeUseCase(if (article == articles[0]) "Cafetera" else article, brand, pages)
                    }.flatten()
            addProcessedProducts(storeProducts)
            products.addAll(storeProducts)
        } else {
            val storeProducts = storeUseCase(if (article == articles[0]) "Cafetera" else article, page = pages)
            addProcessedProducts(storeProducts)
            products.addAll(storeProducts)
        }
    }

}