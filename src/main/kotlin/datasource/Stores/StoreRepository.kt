package datasource.Stores

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * Created by ConradoMateu on 26/10/2017.
 */

interface StoreRepository{
    var driver: WebDriver
    fun browse()
    fun findElement(by: By): WebElement
    fun findElements(by:By): List<WebElement>
    fun search(element: WebElement,text: String)
}
