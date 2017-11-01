/**
 * Created by ConradoMateu on 26/10/2017.
 */

object CONSTANTS{

    fun getOSExtension(): String {
        return if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            ".exe"
        } else {
            ""
        }
    }

    object CHROME{
        val TYPE = "webdriver.chrome.driver"
        val PATH = System.getProperty("user.dir")+"/Chrome/chromedriver${getOSExtension()}"
    }

    object AMAZON{
        val URL = "https://www.amazon.es"
    }

    object CORTEINGLES{
        val URL = "https://elcorteingles.es"
    }
}