import domain.Product
import domain.TableItemProduct
import domain.mapToTableItem
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
            column("Marca", TableItemProduct::brand)
            column("Modelo", TableItemProduct::identifier)
            column("El Corte Ingles", TableItemProduct::corteIngles).cellFormat { formatPriceCell(it) }
            column("Fnac", TableItemProduct::fnac).cellFormat { formatPriceCell(it) }
            column("Amazon", TableItemProduct::amazon).cellFormat { formatPriceCell(it) }
            column("Nombre", TableItemProduct::name).remainingWidth()
        }
    }

    private fun TableCell<TableItemProduct, Double>.formatPriceCell(it: Double) {
        text = if (it == -1.0) {
            "-"
        } else {
            it.toString()
        }
    }
}
