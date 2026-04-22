import config.HttpClientFactory
import org.junit.jupiter.api.AfterAll

open class BaseTest {

    companion object {

        @JvmStatic
        @AfterAll
        fun tearDown() {
            HttpClientFactory.close()
        }
    }
}
