package hu.vanio.kotlin.hazifeladat.ms.dto

import java.time.LocalDateTime

data class ForecastDTO(
    var latitude: Double,
    var longitude: Double,
    var generationtime_ms: Long,
    var utc_offset_seconds: Int,
    var timezone: String,
    var timezone_abbreviation: String,
    var elevation: Double,
    var hourly_units: HourlyUnitsDTO,
    var hourly: HourlyDTO,
)

data class HourlyDTO(
    var time: List<LocalDateTime>,
    var temperature_2m: List<Double>,
) {
    fun getDates() = time.map { it.toLocalDate() }.distinct()
}

data class HourlyUnitsDTO(
    var time: String,
    var temperature_2m: String,
)