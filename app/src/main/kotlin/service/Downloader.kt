package hu.vanio.kotlin.hazifeladat.ms.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import hu.vanio.kotlin.hazifeladat.ms.dto.ForecastDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@Service
class Downloader(val restTemplate: RestTemplate) {
    private val log = LoggerFactory.getLogger(Downloader::class.java)

    fun download(): ForecastDTO {

        val uri = UriComponentsBuilder.fromPath("/forecast")
            // @formatter:off
            .queryParam("latitude", "47.4984")
            .queryParam("longitude", "19.0404")
            .queryParam("hourly", "temperature_2m")
            .queryParam("timezone", "auto")
            .build()
            .toUriString()
        // @formatter:on
        log.debug("HTTP kliens url: [$uri]")

        val resultString: String? = try {
            restTemplate.getForObject(uri, String::class.java)
        } catch (e: Exception) {
            log.error("Nem sikerült a lekérdezés", e)
            throw IOException("Nem sikerült a lekérdezés", e)
        }

        if (resultString == null) {
            log.error("Nem sikerült a lekérdezés")
            throw IOException("Nem sikerült a lekérdezés")
        }
        log.debug("Result: $resultString")

        val result: ForecastDTO = ObjectMapper().apply {
            registerModule(
                KotlinModule.Builder().build()
            )
            registerModule(JavaTimeModule())
        }.readValue<ForecastDTO>(resultString)
        log.debug(result.toString())

        return result
    }
}