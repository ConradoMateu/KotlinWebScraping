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
                    .filter {
                        val currentBrand = it.findElement(By.cssSelector(".brand.c12")).text
                        currentBrand.toLowerCase() == brand!!.toLowerCase()
                    }
                    .map {
                        val productBrand = it.findElement(By.xpath("//div[contains(@class,'product-name')]/h4")).text
                        val productName= it.findElement(By.className("js-product-click")).getAttribute("title")
                        val productPrice = it.findElement(By.className("current")).text.removeSuffix("€").replace(",",".").toDouble()
//                        if (productBrand.toLowerCase() == brand!!.toLowerCase()){
                            Product(productName, productBrand, productPrice, "CorteIngles")

                    }
            result.addAll(items)

            try {
                val nextPage = webDriver.findElement(By.xpath("//*[@id=\"product-list\"]/div[3]/ul/li[4]/a")).getAttribute("href")
                webDriver.browse(nextPage)
            } catch (ex: Exception) {
                return result
            }
        }

        return result
    }

    private fun selectBrand(brand: String) {
//            val brands = webDriver.findElement(By.cssSelector("#filters > li.geci-search-desktop.sliding.hidden-m.hidden-s.hidden-xs.hidden-xxs.geci-search.geci-search-current > ul:nth-child(3)"))
//            val brandsList = brands.findElements(By.cssSelector(".event.facet-popup"))
            val brands = webDriver.findElement(By.xpath("//*[@id=\"filters\"]/li[2]/ul[1]"))
            val brandsList = brands.findElements(By.className("facet-popup"))
            val filteredBrandsList = brandsList.filter { it.getAttribute("title").toLowerCase() == brand.toLowerCase() }

            if (filteredBrandsList.isNotEmpty()) {
                filteredBrandsList.first().click()
                return
            } else {
                try {
                    webDriver.findElement(By.cssSelector("#filters > li.geci-search-desktop.sliding.hidden-m.hidden-s.hidden-xs.hidden-xxs.geci-search.geci-search-current > ul:nth-child(3) > li:nth-child(11) > a")).click()
                    val brandsList = webDriver.findElements(By.id(brand))
                    val filteredBrandsList = brandsList.filter { it.getAttribute("title").toLowerCase() == brand.toLowerCase() }

                    if (filteredBrandsList.isNotEmpty()) {
                        filteredBrandsList.first().click()
                        webDriver.findElement(By.xpath("//*[@id=\"mdl-url-filter\"]")).click()
                        return
                    }

                    throw BrandNotFoundException()
                } catch (e: Exception) {
                    throw BrandNotFoundException()
                }
            }
    }

}