package hu.vanio.kotlin.hazifeladat.ms.app

import hu.vanio.kotlin.hazifeladat.ms.service.Downloader
import hu.vanio.kotlin.hazifeladat.ms.web.WeatherWebApp
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import kotlin.test.Test

@SpringBootTest(classes = arrayOf(WeatherWebApp::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherAppTest(@Autowired val restTemplate: TestRestTemplate) {
    @Autowired
    private lateinit var downloader: Downloader

    @Test
    fun `sikeres lekerdezes`() {
        // Given

        // When
        val entity = restTemplate.getForEntity<String>("/")

        // Then
        assertEquals(HttpStatus.OK, entity.statusCode)
        assertTrue(entity.body?.contains("<h1>Napi bontású előrejelzés</h1>") ?: false)
    }

    @Test
    fun `tartalmaz adatot`() {
        // Given

        // When
        val forecastDTO = downloader.download()

        // Then
        assert(forecastDTO.hourly.time.size > 0)
        assert(forecastDTO.hourly.temperature_2m.size > 0)

    }
}