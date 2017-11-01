import di.kdi
import domain.Product
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.observable
import usecases.GetProcessedProducts

class ResultsActivityPresenter : Controller() {

    private val getProcessedProducts: GetProcessedProducts by kdi()

    val products: ObservableList<Product> = mutableListOf<Product>().observable()

    init {
        products.addAll(getProcessedProducts())
    }

}