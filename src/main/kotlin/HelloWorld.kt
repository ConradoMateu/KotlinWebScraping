import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver


fun main(args: Array<String>) {
    System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/Chrome/chromedriver")
    var webdriver: WebDriver = ChromeDriver()
    webdriver.get("https://amazon.es")
    var searchBar= webdriver.findElement(By.id("twotabsearchtextbox"))
    searchBar.sendKeys("Cafeteras")
    searchBar.submit()



    val cafeteras: List<WebElement> = webdriver.findElements( By.xpath("//*[contains(@class, 'a-link-normal a-text-normal')]"))
    print(cafeteras.first().getAttribute("href"))
}