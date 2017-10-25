import tornadofx.Controller
import tornadofx.observable

class MainActivityPresenter : Controller() {
    val articles = listOf("Todos", "Cafetera", "Cafetera goteo").observable()
    val brands = listOf("Nespresso", "Tassimo").observable()

    private val selectedBrands: List<Pair<String, Boolean>> = brands.map { Pair(it, false) }
    var selectedArticle: String = articles[0]

    fun handleSelectBrand(brand: String, isSelected: Boolean) {
        selectedBrands.map {
            if (it.first == brand) Pair(brand, isSelected) else it
        }
    }
}