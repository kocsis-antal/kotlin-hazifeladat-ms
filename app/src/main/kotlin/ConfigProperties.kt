import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "meteo-api")
data class MeteoProperties(
    val url: String? = null
)
