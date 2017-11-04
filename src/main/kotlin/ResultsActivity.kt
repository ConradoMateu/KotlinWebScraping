import domain.Product
import javafx.scene.Scene
import javafx.stage.Stage
import tornadofx.*
import usecases.GetProcessedProducts

class ResultsActivity(private val presenter: ResultsActivityPresenter) : View() {

    companion object {
        fun navigateWithPreviousResults() {
            val presenter = ResultsActivityPresenter(GetProcessedProducts())
            val resultsActivity = ResultsActivity(presenter)
            presenter.showStoredData()
            val stage = Stage()
            stage.scene = Scene(resultsActivity.root)
            stage.show()
        }

        fun navigateWithResults(results: List<Product>) {
            val presenter = ResultsActivityPresenter(GetProcessedProducts())
            val resultsActivity = ResultsActivity(presenter)
            presenter.showSpecificData(results)
            val stage = Stage()
            stage.scene = Scene(resultsActivity.root)
            stage.show()
        }
    }

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
