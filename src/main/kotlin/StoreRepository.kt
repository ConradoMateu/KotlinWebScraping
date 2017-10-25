import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.sql.Driver

/**
 * Created by ConradoMateu on 25/10/2017.
 */

interface StoreRepository{
    var driver: WebDriver
    fun browse(text: String)
    fun findElement(id: String): WebElement
    fun findElements(by: By,text: String): List<WebElement>
    fun configureWebDriver(type: String = "webdriver.chrome.driver", path: String = System.getProperty("user.dir")+"/Chrome/chromedriver")
}