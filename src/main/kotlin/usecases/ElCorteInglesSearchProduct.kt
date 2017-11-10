package usecases

import datasource.BrandDAO
import datasource.Stores.StoreRepository
import di.kdi
import domain.BrandNotFoundException
import domain.Product
import domain.parseDouble
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class ElCorteInglesSearchProduct: ISearchProducts() {
    override val webDriver: StoreRepository by kdi(CONSTANTS.CORTEINGLES.URL)

    override operator fun invoke(productName: String, brand: String?, page: Int): List<Product> {
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
                        val brand = it.findElement(By.className("brand")).text.trim().toLowerCase().capitalize()
                        val productName = it.findElement(By.className("js-product-click")).getAttribute("title")
                        val price = it.findElement(By.className("product-price"))
                                .findElement(By.className("current")).text.parseDouble()
                        Product(productName, brand, mapOf("CorteIngles" to price), extractBuzzWords(productName))
                    }

            result += if (brand != null) {
                items.filter { it.brand.toLowerCase() == brand.toLowerCase() }
            } else {
                items
            }

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
                val brandsList = webDriver.findElements(By.className("facet-popup"))
                val filteredBrandsList = brandsList.filter { it.getAttribute("title").toLowerCase() == brand.toLowerCase() }

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