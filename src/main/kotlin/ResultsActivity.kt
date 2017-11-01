import domain.Product
import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.*

class ResultsActivity : View() {
    private val presenter: ResultsActivityPresenter by inject()


    override val root = borderpane {
        prefWidth = 1350.0
        prefHeight = 850.0

        center = tableview(presenter.products) {
            column("Brand", Product::brand)
            column("Price", Product::price)
            column("Store", Product::store)
            column("Name", Product::name).remainingWidth()
        }
    }
}
