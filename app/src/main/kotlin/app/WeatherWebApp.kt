package hu.vanio.kotlin.hazifeladat.ms.web

import hu.vanio.kotlin.hazifeladat.ms.MeteoProperties
import hu.vanio.kotlin.hazifeladat.ms.config.RestTemplateConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile


@SpringBootApplication
@ComponentScan("hu.vanio.kotlin.hazifeladat.ms.service, hu.vanio.kotlin.hazifeladat.ms.web")
@Import(RestTemplateConfig::class)
@EnableConfigurationProperties(MeteoProperties::class)
class WeatherWebApp

@Profile("web")
fun main() {
    runApplication<WeatherWebApp> {}
}
