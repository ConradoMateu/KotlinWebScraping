import javafx.scene.control.ComboBox
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*

class MainActivity : View() {
    private val presenter: MainActivityPresenter by inject()

    private lateinit var brandsListView: ListView<String>
    private lateinit var comboArticles: ComboBox<String>
    private val selectedStores = mutableMapOf(
            "https://www.amazon.es" to true,
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
                checkbox("El corte inglés") {
                    isSelected = true
                    action { selectedStores.replace("https://www.elcorteingles.es", isSelected) }
                }
            }
        }

        hbox {
            vgrow = Priority.ALWAYS
            vbox(6) {
                hgrow = Priority.ALWAYS

                label("Seleccione las marcas")
                brandsListView = listview(presenter.brands) {
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
                        if (brands.isEmpty()) {
                            presenter.searchItems(article, presenter.brands, selectedStores)
                        } else {
                            presenter.searchItems(article, brands, selectedStores)
                        }
                    }
                }
            }
        }

    }

    init {
        root.stylesheets.add(javaClass.classLoader.getResource("bootstrap3.css").toExternalForm())
    }
}
