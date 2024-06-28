package hu.vanio.kotlin.hazifeladat.ms

import ForecastDTO
import MeteoProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriBuilder


@SpringBootApplication
@EnableConfigurationProperties(MeteoProperties::class)
class WeatherApp(var meteoProperties: MeteoProperties) : CommandLineRunner {
    private val log = LoggerFactory.getLogger(WeatherApp::class.java)

    fun doIt() {
        if (meteoProperties.url == null) {
            println("Nincs megadva URL")
            return
        }

        val restClient: RestClient = RestClient.builder().baseUrl(meteoProperties.url).build()
        log.info(
            "Read Meteo configuration parameters:\n" + "\turl: [${meteoProperties.url}]"
        )

        val resultString: String? = restClient.get().uri { uriBuilder: UriBuilder ->
            uriBuilder.path("forecast").queryParam("latitude", "47.4984").queryParam("longitude", "19.0404").queryParam("hourly", "temperature_2m").queryParam("timezone", "auto").build()
        }.header("Content-Type", "application/json").retrieve().body(String::class.java)
        if (resultString == null) {
            println("Nem sikerült a lekérdezés")
            return
        }
        log.info("Result: $resultString")

        val result: ForecastDTO = ObjectMapper().apply {
            registerModule(
                KotlinModule.Builder().build()
            )
            registerModule(JavaTimeModule())
        }.readValue<ForecastDTO>(resultString)
        println(result)
    }

    override fun run(vararg args: String?) {
        doIt()
    }
}

fun main() {
    runApplication<WeatherApp> {
        webApplicationType = org.springframework.boot.WebApplicationType.NONE
    }
}
