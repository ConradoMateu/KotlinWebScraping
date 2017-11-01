package usecases

import datasource.Stores.StoreRepository
import di.kdi
import domain.Product
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions

class AmazonSearchProduct : ISearchProducts {

    override val webDriver: StoreRepository by kdi(CONSTANTS.AMAZON.URL)

    operator fun invoke(productName: String, page: Int = 1): List<Product> {
        val result = mutableListOf<Product>()

        webDriver.browse(CONSTANTS.AMAZON.URL)
        val inputBar = webDriver.findElement(By.id("twotabsearchtextbox"))

        webDriver.search(inputBar, productName)
        webDriver.waitForPageToLoad()

        (1..page).forEach {
            val items = webDriver.findElements(By.className("s-item-container"))
                    .map {
                        val productName = it.findElement(By.className("s-access-detail-page"))
                                .getAttribute("title")
                        val brand = it.findElements(By.className("a-size-small"))[1].text
                        val price = it.findElement(By.className("a-color-price")).text.removePrefix("EUR ")
                        Product(productName, brand, price, "Amazon")
                    }
            result.addAll(items)

            val nextPage = webDriver.findElement(By.id("pagnNextLink")).getAttribute("href")
            webDriver.browse(nextPage)
        }

        return result
    }

}