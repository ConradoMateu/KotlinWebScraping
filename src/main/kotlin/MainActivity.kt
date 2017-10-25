import javafx.geometry.Insets
import javafx.scene.control.ComboBox
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class MainActivity : View() {
    override val root: VBox = VBox()
    private val presenter: MainActivityPresenter by inject()

    private lateinit var brandsListView: ListView<String>
    private lateinit var comboArticles: ComboBox<String>


    init {
        root.stylesheets.add(javaClass.classLoader.getResource("bootstrap3.css").toExternalForm())
        vbox(16) {
            hbox(6) {
                label("Seleccione un artículo")
                comboArticles = combobox(values = presenter.articles).apply {
                    value = presenter.articles[0]
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
                        presenter.searchItems(article, brands)
                    }
                }
            }
        }

    }
}