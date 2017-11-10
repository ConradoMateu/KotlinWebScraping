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
            val items = webDriver.findElements(By.className("product-preview "))
                    .map {

                        val brand = it.findElement(By.xpath("//div[contains(@class,'product-name')]/h4")).text


                        //*[@id="product-list"]/ul/li[1]/div/div[2]/div[1]/h4#product-list > ul > li.product.cl2.cl3.cl4 > div > div.info > div.product-name > h4

                        //*[@id="product-list"]/ul/li[1]/div/div[2]/div[1]/h3

                        val productName= it.findElement(By.className("js-product-click")).getAttribute("title")
                        val productPrice = it.findElement(By.className("current")).text.removeSuffix("€").replace(",",".").toDouble()
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
        val brands = webDriver.findElement(By.cssSelector("#filters > li.geci-search-desktop.sliding.hidden-m.hidden-s.hidden-xs.hidden-xxs.geci-search.geci-search-current > ul:nth-child(3)"))
        val brandsList = brands.findElements(By.cssSelector(".event.facet-popup"))
        val filteredBrandsList = brandsList.filter { it.getAttribute("title").toLowerCase() == brand.toLowerCase() }

        if (filteredBrandsList.isNotEmpty()) {
            filteredBrandsList.first().click()
            return
        } else {
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

}