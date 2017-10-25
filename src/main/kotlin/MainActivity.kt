import javafx.scene.layout.VBox
import tornadofx.*

class MainActivity : View() {
    override val root: VBox = VBox()
    private val presenter : MainActivityPresenter by inject()

    init {
        hbox {
            label("Seleccione un artículo")
            combobox(values = presenter.articles).apply {
                value = presenter.selectedArticle
                valueProperty()
                    .onChange { presenter.selectedArticle = it!! }
            }
        }

        hbox {
            vbox {
                label("Seleccione las marcas")
                presenter.brands.map {
                    checkbox(it) {
                        action { presenter.handleSelectBrand(it, isSelected) }
                    }
                }
            }
        }

    }
}