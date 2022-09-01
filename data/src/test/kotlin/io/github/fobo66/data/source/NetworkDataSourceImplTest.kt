package io.github.fobo66.data.source

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.create
import de.jensklingenberg.ktorfit.ktorfit
import io.github.fobo66.data.fake.FakeNetworkDataSource
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import java.util.concurrent.Executors
import kotlin.test.BeforeTest
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
                respondOk(FakeNetworkDataSource.response)
            } else {
                respondError(HttpStatusCode.InternalServerError)
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
                        },
                        contentType = ContentType.Any
                    )
                }
            }
        }
    }

    private lateinit var networkDataSource: NetworkDataSource

    @BeforeTest
    fun setUp() {
        networkDataSource = NetworkDataSourceImpl(
            ktorfit.create(),
            Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchRating() = runTest {
        val result = networkDataSource.fetchRating(1L)
        assertNotNull(result)
    }
}
