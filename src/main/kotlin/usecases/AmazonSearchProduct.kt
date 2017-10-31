package usecases

import datasource.Stores.StoreRepository
import di.kdi
import domain.Product
import org.openqa.selenium.By

class AmazonSearchProduct : ISearchProducts {

    override val webDriver: StoreRepository by kdi("https://www.amazon.es")

    operator fun invoke(productName: String): List<Product> {
        webDriver.browse()
        val inputBar = webDriver.findElement(By.id("twotabsearchtextbox"))

        webDriver.search(inputBar, productName)
        webDriver.waitForPageToLoad()

        val items = webDriver.findElements(By.className("s-item-container"))

        return items
                .map {
                    it.findElement(By.className("s-access-detail-page"))
                            .getAttribute("title")
                }
                .map { Product(it) }
    }

}