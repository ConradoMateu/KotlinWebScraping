package datasource.Stores

import java.util.concurrent.TimeUnit

class FnacRepository : StoreRepository() {
    override fun waitForPageToLoad() {
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS)
    }
}