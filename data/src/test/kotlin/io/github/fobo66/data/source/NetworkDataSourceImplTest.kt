/*
 *    Copyright 2022 Andrey Mukamolov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertNotNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkDataSourceImplTest {

    private val engine = MockEngine {
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

    private val ktorfit: Ktorfit = ktorfit {
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

    private val dispatcher: ExecutorCoroutineDispatcher =
        Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private val networkDataSource: NetworkDataSource = NetworkDataSourceImpl(
        ktorfit.createMatchmakingRatingApi(),
        dispatcher
    )

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
