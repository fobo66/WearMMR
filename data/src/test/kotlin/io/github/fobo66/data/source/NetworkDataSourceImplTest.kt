package io.github.fobo66.data.source

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import io.github.fobo66.data.api.createMatchmakingRatingApi
import io.github.fobo66.data.fake.FakeNetworkDataSource
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import java.util.concurrent.Executors
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertNotNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkDataSourceImplTest {

    private val engine: MockEngine by lazy {
        MockEngine {
            if (it.url.encodedPath.contains('1')) {
                respond(
                    FakeNetworkDataSource.response,
                    headers = headersOf("Content-Type", "application/json")
                )
            } else {
                respond(
                    content = "",
                    status = HttpStatusCode.InternalServerError,
                    headers = headersOf("Content-Type", "application/json")
                )
            }
        }
    }

    private val ktorfit: Ktorfit by lazy {
        ktorfit {
            baseUrl("https://localhost/")
            httpClient(engine) {
                install(ContentNegotiation) {
                    json(
                        json = Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }
            }
        }
    }

    private lateinit var networkDataSource: NetworkDataSource

    private lateinit var dispatcher: ExecutorCoroutineDispatcher

    @BeforeTest
    fun setUp() {
        dispatcher =
            Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        networkDataSource = NetworkDataSourceImpl(
            ktorfit.createMatchmakingRatingApi(),
            dispatcher
        )
    }

    @AfterTest
    fun tearDown() {
        dispatcher.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchRating() = runTest {
        val result = networkDataSource.fetchRating(1L)
        assertNotNull(result)
    }
}
