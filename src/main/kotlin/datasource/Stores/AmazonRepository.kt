package datasource.Stores

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.util.concurrent.TimeUnit

/**
 * Created by ConradoMateu on 26/10/2017.
 */

class AmazonRepository : StoreRepository() {

    override fun browse() {
        driver.get(CONSTANTS.AMAZON.URL)
    }

    override fun findElement(by: By): WebElement {
        return driver.findElement(by)
    }

    override fun findElements(by: By): List<WebElement> {
        return driver.findElements(by)
    }

    override fun search(element: WebElement,text: String){
        element.sendKeys(text)
        element.submit()
    }

    override fun waitForPageToLoad() {
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS)
    }


}