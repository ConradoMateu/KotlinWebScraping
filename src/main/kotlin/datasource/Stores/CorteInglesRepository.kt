package datasource.Stores

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * Created by ConradoMateu on 27/10/2017.
 */

class CorteInglesRepository(override var driver: WebDriver) : StoreRepository{


    override fun browse() {
        driver.get(CONSTANTS.AMAZON.URL)
    }

    override fun findElement(by: By): WebElement {
        return driver.findElement(by)
    }

    override fun findElements(by: By): List<WebElement> {
        return driver.findElements(by)
    }

    override fun configureWebDriver(type: String, path: String) {
        System.setProperty(type,path)
    }

    override fun search(element: WebElement, text: String){
        element.sendKeys(text)
        element.submit()
    }


}