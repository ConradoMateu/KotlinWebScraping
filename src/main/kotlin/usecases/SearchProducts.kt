package usecases

import Application
import base.UseCase
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import datasource.Stores.StoreRepository
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class SearchProducts(store: String) : UseCase<WebElement> {

    private val webDriver: StoreRepository by Application.kodein.lazy.instance(store)

    override fun execute(): WebElement {
        webDriver.browse()
        return webDriver.findElement(By.id("cafetera"))
    }

}