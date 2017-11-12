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
        val PATH = "./bin/chromedriver${getOSExtension()}"
    }

    object AMAZON{
        val URL = "https://www.amazon.es"
    }

    object FNAC {
        val URL = "https://www.fnac.es"
    }

    object CORTEINGLES{
        val URL = "https://elcorteingles.es"
    }
}