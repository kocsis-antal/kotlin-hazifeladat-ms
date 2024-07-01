package hu.vanio.kotlin.hazifeladat.ms.cli

import hu.vanio.kotlin.hazifeladat.ms.MeteoProperties
import hu.vanio.kotlin.hazifeladat.ms.config.RestTemplateConfig
import hu.vanio.kotlin.hazifeladat.ms.service.Calculator
import hu.vanio.kotlin.hazifeladat.ms.service.Downloader
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile


@SpringBootApplication
@ComponentScan("hu.vanio.kotlin.hazifeladat.ms.service")
@Import(RestTemplateConfig::class)
@EnableConfigurationProperties(MeteoProperties::class)
class WeatherApp(var downloader: Downloader, var calculator: Calculator) : CommandLineRunner {
    private val log = LoggerFactory.getLogger(WeatherApp::class.java)

    fun doIt() {
        val forecastDTO = downloader.download()
        log.debug(forecastDTO.toString())

        calculator.calculate(forecastDTO).dailyStatistics.forEach {
            log.info("Day: ${it.day}, min: ${it.minimumTemperature}${forecastDTO.hourly_units.temperature_2m}, avg: ${it.averageTemperature}${forecastDTO.hourly_units.temperature_2m}, max: ${it.maximumTemperature}${forecastDTO.hourly_units.temperature_2m}")
        }
    }

    override fun run(vararg args: String?) {
        doIt()
    }
}

@Profile("!web")
fun main() {
    runApplication<WeatherApp> {
        webApplicationType = org.springframework.boot.WebApplicationType.NONE
    }
}
