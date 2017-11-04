import domain.Product
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.observable
import usecases.GetProcessedProducts

class ResultsActivityPresenter(private val getProcessedProducts: GetProcessedProducts) : Controller() {

    var products: ObservableList<Product> = mutableListOf<Product>().observable()

    fun showStoredData() {
        products.addAll(getProcessedProducts())
    }

    fun showSpecificData(data: List<Product>) {
        products.addAll(data)
    }

}