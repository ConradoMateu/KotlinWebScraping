import domain.Product
import javafx.scene.Scene
import javafx.scene.control.TableCell
import javafx.scene.paint.Color
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
            column("Marca", Product::brand)
            column("El Corte Ingles", Product::corteIngles).cellFormat { formatPriceCell(it) }
            column("Fnac", Product::fnac).cellFormat { formatPriceCell(it) }
            column("Amazon", Product::amazon).cellFormat { formatPriceCell(it) }
            column("Nombre", Product::name).remainingWidth()
        }
    }

    private fun TableCell<Product, Double>.formatPriceCell(it: Double) {
        text = if (it == -1.0) {
            "-"
        } else {
            it.toString()
        }
    }
}
