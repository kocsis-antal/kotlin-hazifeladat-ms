package hu.vanio.kotlin.hazifeladat.ms.app

import hu.vanio.kotlin.hazifeladat.ms.service.Downloader
import hu.vanio.kotlin.hazifeladat.ms.web.WeatherWebApp
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import kotlin.test.Test

private const val URI = "forecast?latitude=47.4984&longitude=19.0404&hourly=temperature_2m&timezone=auto"

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = arrayOf(WeatherWebApp::class))
@RestClientTest(Downloader::class)
class WebServerTest {
    @Autowired
    private lateinit var downloader: Downloader

    @Autowired
    private lateinit var server: MockRestServiceServer

    @Test
    fun `sikeres lekerdezes`() {
        // Given
        server.expect(MockRestRequestMatchers.requestTo(URI))
            .andRespond(MockRestResponseCreators.withSuccess(body(), MediaType.APPLICATION_JSON))

        // When + Then
        assertDoesNotThrow { downloader.download() }
    }

    @Test
    fun `tartalmaz adatot`() {
        // Given
        server.expect(MockRestRequestMatchers.requestTo(URI))
            .andRespond(MockRestResponseCreators.withSuccess(body(), MediaType.APPLICATION_JSON))

        // When
        val forecastDTO = downloader.download()

        // Then
        assert(forecastDTO.hourly.time.size > 0)
        assert(forecastDTO.hourly.temperature_2m.size > 0)
    }

    private fun body() = """{
                              "latitude": 47.5,
                              "longitude": 19.0625,
                              "generationtime_ms": 0.026106834411621094,
                              "utc_offset_seconds": 7200,
                              "timezone": "Europe/Budapest",
                              "timezone_abbreviation": "CEST",
                              "elevation": 124.0,
                              "hourly_units": {
                                "time": "iso8601",
                                "temperature_2m": "°C"
                              },
                              "hourly": {
                                "time": [
                                  "2024-06-29T00:00",
                                  "2024-06-29T01:00",
                                  "2024-06-29T02:00"
                                ],
                                "temperature_2m": [
                                  23.0,
                                  22.4,
                                  21.8
                                ]
                              }
                            }""".trimIndent()
}