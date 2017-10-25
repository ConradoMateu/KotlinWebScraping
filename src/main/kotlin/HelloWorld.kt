import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

/**
 * Created by ConradoMateu on 25/10/2017.
 */


fun main(args: Array<String>) {
    System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/Chrome/chromedriver")
    var webdriver: WebDriver = ChromeDriver()
    webdriver.get("https://poliformat.upv.es")
}