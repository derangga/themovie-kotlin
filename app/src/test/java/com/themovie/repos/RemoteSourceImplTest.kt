package com.themovie.repos

import com.themovie.helper.FileReader
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.Result
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HttpsURLConnection

@RunWith(JUnit4::class)
class RemoteSourceImplTest{
    private lateinit var apiInterface: ApiInterface
    private lateinit var mockWebServer: MockWebServer
    private lateinit var remoteSource: RemoteSource

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiInterface = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        remoteSource = RemoteSourceImpl(apiInterface)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `request trending movie response success`() {
        runBlocking {
            // given
            enqueueRequest(HttpsURLConnection.HTTP_OK, true,"movie.json")

            // when
            val resultResponse = remoteSource.getPopularMovie(1)
            val movie = resultResponse.data?.results?.get(0)

            // then
            assertNotNull(resultResponse)
            assertThat(resultResponse.data?.results?.size, CoreMatchers.`is`(20))
            assertThat(movie?.id, CoreMatchers.`is`(419704))
            assertThat(movie?.title, CoreMatchers.`is`("Ad Astra"))
        }
    }

    @Test
    fun `request trending movie response error`() {
        runBlocking {
            // given
            enqueueRequest(HttpsURLConnection.HTTP_INTERNAL_ERROR)

            // when
            val resultResponse = remoteSource.getPopularMovie(1)

            // then
            assertThat(resultResponse.status, CoreMatchers.`is`(Result.Status.ERROR))
            assertNull(resultResponse.data)
        }
    }

    @Test
    fun `request discover tv response success`() {
        runBlocking {
            // given
            enqueueRequest(HttpsURLConnection.HTTP_OK, true, "tv.json")

            // when
            val resultResponse = remoteSource.getDiscoverTv(1)
            val tv = resultResponse.data?.results?.get(0)

            // then
            assertNotNull(resultResponse)
            assertThat(resultResponse.data?.results?.size, CoreMatchers.`is`(20))
            assertThat(tv?.id, CoreMatchers.`is`(70523))
            assertThat(tv?.name, CoreMatchers.`is`("Dark"))
        }
    }

    @Test
    fun `request discover tv response error`() {
        runBlocking {
            // given
            enqueueRequest(HttpsURLConnection.HTTP_INTERNAL_ERROR)

            // when
            val resultResponse = remoteSource.getDiscoverTv(1)

            // then
            assertThat(resultResponse.status, CoreMatchers.`is`(Result.Status.ERROR))
            assertNull(resultResponse.data)
        }
    }

    private fun enqueueRequest(responseCode: Int, shouldGiveResponse: Boolean = false, fileName: String = ""){
        val response = MockResponse()
            .setResponseCode(responseCode)
        if(shouldGiveResponse) response.setBody(FileReader.readTestResourceFile(fileName))
        mockWebServer.enqueue(response)
    }
}