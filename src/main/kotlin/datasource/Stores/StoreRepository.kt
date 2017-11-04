package datasource.Stores

import di.kdi
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions

/**
 * Created by ConradoMateu on 26/10/2017.
 */

abstract class StoreRepository {
    val driver: WebDriver = ChromeDriver()
    abstract fun waitForPageToLoad()

    fun browse(str: String) {
        driver.get(str)
    }

    fun findElement(by: By): WebElement {
        return driver.findElement(by)
    }

    fun findElements(by: By): List<WebElement> {
        return driver.findElements(by)
    }

    fun search(element: WebElement, text: String){
        element.sendKeys(text)
        element.submit()
    }

    fun moveToElement(by: By): WebElement {
        val element = findElement(by)
        Actions(driver).moveToElement(element).perform()
        return element
    }

    fun moveToElement(element: WebElement) {
        (driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", element)
        Thread.sleep(1000)
    }

    fun destroy() {
        driver.close()
    }
}
