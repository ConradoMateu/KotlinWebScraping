import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver


fun main(args: Array<String>) {
    System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/Chrome/chromedriver")
    var webdriver: WebDriver = ChromeDriver()
    webdriver.get("https://poliformat.upv.es")
}