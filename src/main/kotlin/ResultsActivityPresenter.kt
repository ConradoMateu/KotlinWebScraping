import domain.Product
import domain.TableItemProduct
import domain.mapToTableItem
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.observable
import usecases.GetProcessedProducts

class ResultsActivityPresenter(private val getProcessedProducts: GetProcessedProducts) : Controller() {

    var products: ObservableList<TableItemProduct> = mutableListOf<TableItemProduct>().observable()

    fun showStoredData() {
        products.addAll(getProcessedProducts().map(Product::mapToTableItem))
    }

    fun showSpecificData(data: List<Product>) {
        products.addAll(data.map(Product::mapToTableItem))
    }

}