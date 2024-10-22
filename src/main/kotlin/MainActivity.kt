import javafx.scene.control.*
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*

class MainActivity : View() {
    private val presenter: MainActivityPresenter by inject()

    private lateinit var brandsListView: ListView<String>
    private lateinit var comboArticles: ComboBox<String>
    private lateinit var pagesNumber: Spinner<Int>

    private val selectedStores = mutableMapOf(
            "https://www.amazon.es" to true,
            "https://www.fnac.es" to true,
            "https://www.elcorteingles.es" to true)

    override val root = vbox(16) {
        hbox(6) {
            vbox(4) {
                label("Seleccione un artículo")
                comboArticles = combobox(values = presenter.articles).apply {
                    value = presenter.articles[0]
                }

                label("Seleccione tiendas")
                checkbox("Amazon") {
                    isSelected = true
                    action { selectedStores.replace("https://www.amazon.es", isSelected) }
                }
                checkbox("Fnac") {
                    isSelected = true
                    action { selectedStores.replace("https://www.fnac.es", isSelected) }
                }
                checkbox("El corte inglés") {
                    isSelected = true
                    action { selectedStores.replace("https://www.elcorteingles.es", isSelected) }
                }
            }
        }


        vbox(4) {
            label("Numero de paginas")
            pagesNumber = spinner(min = 1, max = 5, initialValue = 1, amountToStepBy = 1)
        }


        hbox {
            vgrow = Priority.ALWAYS
            vbox(6) {
                hgrow = Priority.ALWAYS

                label("Seleccione las marcas")
                brandsListView = listview(presenter.brands.sorted()) {
                    selectionModel.selectionMode = SelectionMode.MULTIPLE
                }
            }
        }

        hbox {
            button("Search!") {
                useMaxWidth = true
                hgrow = Priority.ALWAYS
                styleClass.add("primary")

                action {
                    val article = comboArticles.value
                    val brands = brandsListView.selectionModel.selectedItems

                    if (selectedStores.any { (_, value) -> value }) {
                        presenter.searchItems(article, brands, selectedStores, pagesNumber.value)
                    }
                }
            }
        }

    }

    init {
        root.stylesheets.add(javaClass.classLoader.getResource("bootstrap3.css").toExternalForm())
    }
}
