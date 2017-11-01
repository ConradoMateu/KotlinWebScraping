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

    override fun waitForPageToLoad() {
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS)
    }


}