package usecases

import datasource.Stores.AmazonRepository
import datasource.Stores.StoreRepository
import di.kdi
import domain.BrandNotFoundException
import domain.Product
import domain.parseDouble
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class AmazonSearchProduct : ISearchProducts {

    override val webDriver: StoreRepository = AmazonRepository()

    override operator fun invoke(productName: String, brand: String?, page: Int): List<Product> {
        val result = mutableListOf<Product>()

        webDriver.browse(CONSTANTS.AMAZON.URL)
        val inputBar = webDriver.findElement(By.id("twotabsearchtextbox"))

        webDriver.search(inputBar, productName)
        webDriver.waitForPageToLoad()
        Thread.sleep(2000)

        if (brand != null) {
            try {
                this.selectBrand(brand)
                Thread.sleep(1000)
            } catch (e: BrandNotFoundException) {
                webDriver.destroy()
                return result
            }
        }

        (1..page).forEach {
            val items = webDriver.findElements(By.className("s-item-container"))
                    .map {
                        val productName = it.findElement(By.className("s-access-detail-page"))
                                .getAttribute("title")
                        val brand = it.findElements(By.className("a-size-small"))[1].text
                        val price = extractPriceFromElement(it)
                        Product(productName, brand, price, "Amazon")
                    }
            result.addAll(items)

            try {
                val nextPage = webDriver.findElement(By.id("pagnNextLink")).getAttribute("href")
                webDriver.browse(nextPage)
            } catch (e: Exception) {
                webDriver.destroy()
                return result
            }
        }

        webDriver.destroy()
        return result
    }

    private fun extractPriceFromElement(it: WebElement): Double = try {
        it.findElement(By.className("a-color-price"))
                .text.removePrefix("EUR ").parseDouble()
    } catch (ex: Exception) {
        try {
            val natural = it.findElement(By.className("a-price-whole")).text
            val decimal = it.findElement(By.className("a-price-fraction")).text
            (natural + decimal).parseDouble()
        } catch (ex: Exception) {
            it.findElement(By.className("a-color-price"))
                    .text.removeSuffix("€").trim().parseDouble()
        }
    }

    private fun selectBrand(brand: String) {
        val brandsList = webDriver.findElements(By.className("s-ref-link-cursor"))
        val filteredBrandsList = brandsList.filter { it.text.toLowerCase() == brand.toLowerCase() }

        if (filteredBrandsList.isNotEmpty()) {
            filteredBrandsList.first().click()
            return
        } else {
            try {
                webDriver.findElement(By.cssSelector("ul.a-unordered-list:nth-child(12) > li:nth-child(2) > span:nth-child(1) > a:nth-child(1) > span:nth-child(1)")).click()
                val brands = webDriver.findElements(By.className("refinementLink"))
                for (i: WebElement in brands) {
                    if (i.text.toLowerCase() == brand.toLowerCase()) {
                        i.click()
                        return
                    }
                }
                throw BrandNotFoundException()
            } catch (e: Exception) {
                throw BrandNotFoundException()
            }
        }
    }

}