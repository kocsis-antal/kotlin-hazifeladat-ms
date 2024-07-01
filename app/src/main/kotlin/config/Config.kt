package hu.vanio.kotlin.hazifeladat.ms.config

import hu.vanio.kotlin.hazifeladat.ms.MeteoProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(restTemplateBuilder: RestTemplateBuilder, properties: MeteoProperties): RestTemplate {
        val url = properties.url ?: run {
            throw IllegalArgumentException("Nincs megadva URL")
        }

        return restTemplateBuilder.rootUri(url).build()
    }
}