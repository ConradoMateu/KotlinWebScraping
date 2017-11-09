package usecases

import datasource.Stores.StoreRepository
import di.kdi
import domain.BrandNotFoundException
import domain.Product
import domain.parseDouble
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class ElCorteInglesSearchProduct: ISearchProducts {
    override val webDriver: StoreRepository by kdi(CONSTANTS.CORTEINGLES.URL)

    operator fun invoke(productName: String, brand: String? = null, page: Int = 1): List<Product> {
        val result = mutableListOf<Product>()

        webDriver.browse(CONSTANTS.CORTEINGLES.URL)
        val inputBar = webDriver.findElement(By.id("search-box"))

        webDriver.search(inputBar, productName)
        webDriver.waitForPageToLoad()

        if (brand != null) {
            try {
                this.selectBrand(brand)
                Thread.sleep(1000)
            } catch (e: BrandNotFoundException) {
                return result
            }
        }

        (1..page).forEach {
            val items = webDriver.findElements(By.className("product-preview"))
                    .map {
                        val brand = it.findElements(By.cssSelector("ul.a-unordered-list::nth-child(7) > div > div.info > div.product-name > h4"))[1].text
                        val profuctInfo = it.findElement(By.className("info"))
                        val productName = profuctInfo.findElement(By.className("product-name")).findElement(By.className("info-name")).getAttribute("title")
                        val productPrice = profuctInfo.findElement(By.cssSelector("ul.a-unordered-list::nth-child(7) > div > div.info > div.product-price > span.current")).text.toDouble()
                        Product(productName, brand, productPrice, "CorteIngles")
                    }
            result.addAll(items)

            try {
                val nextPage = webDriver.findElement(By.cssSelector("ul.a-unordered-list::nth-child(8)")).getAttribute("href")
                webDriver.browse(nextPage)
            } catch (ex: Exception) {
                return result
            }
        }

        return result
    }

    private fun selectBrand(brand: String) {
            try {
                val brandsList = webDriver.findElements(By.id(brand))
                val filteredBrandsList = brandsList.filter { it.text.toLowerCase() == brand.toLowerCase() }

                if (filteredBrandsList.isNotEmpty()) {
                    filteredBrandsList.first().click()
                    return
                }

                throw BrandNotFoundException()
            } catch (e: Exception) {
                throw BrandNotFoundException()
            }

    }

}