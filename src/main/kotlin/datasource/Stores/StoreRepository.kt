package datasource.Stores

import di.kdi
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * Created by ConradoMateu on 26/10/2017.
 */

abstract class StoreRepository {
    val driver: WebDriver by kdi()
    abstract fun browse()
    abstract fun findElement(by: By): WebElement
    abstract fun findElements(by:By): List<WebElement>
    abstract fun search(element: WebElement,text: String)
    abstract fun waitForPageToLoad()
}
