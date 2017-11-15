package usecases

import datasource.Stores.FnacRepository
import datasource.BrandDAO
import datasource.Stores.StoreRepository
import di.kdi
import domain.BrandNotFoundException
import domain.Product
import domain.parseDouble
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class FnacSearchProduct : ISearchProducts() {

    override val webDriver: StoreRepository = FnacRepository()
    private val allBrandsList: List<String> = brandsDAO.getAll()

    override fun invoke(productName: String, brand: String?, page: Int): List<Product> {
        val result = mutableListOf<Product>()

        webDriver.browse(CONSTANTS.FNAC.URL)
        try {
            webDriver.findElement(By.className("js-Cnilbar-close")).click()
        } catch (ex: Exception) {

        }
        val inputBar = webDriver.findElement(By.id("Fnac_Search"))

        webDriver.search(inputBar, productName)
        webDriver.waitForPageToLoad()

        webDriver.findElement(By.className("mosaicButton")).click()
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
            val items = webDriver.findElements(By.className("Article-mosaicItem"))
                    .mapIndexed { index, it ->
                        val productName = it.findElement(By.className("thumbnail-titleLink")).text
                        val brand = extractBrandFrom(it, productName, index)
                        val price = it.findElement(By.className("thumbnail-price")).text.parseDouble()
                        val identifier = extractBuzzWords(productName) ?: productName
                        Product(productName, brand, mapOf("Fnac" to price), identifier)
                    }
            result.addAll(items)

            try {
                webDriver.findElement(By.className("actionNext")).click()
            } catch (ex: Exception) {
                webDriver.destroy()
                return result
            }
        }

        webDriver.destroy()
        return result

    }

    private fun extractBrandFrom(it: WebElement, productName: String, index: Int): String = try {
        it.findElement(By.cssSelector("#dontTouchThisDiv > ul > li:nth-child(${index + 1}) > div > article > div.thumbnail-sub > span > a")).text
    } catch (ex: Exception) {
        allBrandsList.find { productName.toLowerCase().contains(it.toLowerCase()) } ?: "?"
    }

    private fun selectBrand(brand: String) {
        try {
            webDriver.findElement(By.className("js-toggleFilters-more")).click()
        } catch (ex: Exception) {

        }

        val brandsList = webDriver.findElements(By.className("Filters-choice"))
        val brandItem = brandsList.find { it.getAttribute("title").toLowerCase() == brand.toLowerCase() }

        if (brandItem != null) brandItem.click()
        else throw BrandNotFoundException()

    }
}