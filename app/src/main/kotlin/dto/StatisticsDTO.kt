package hu.vanio.kotlin.hazifeladat.ms.dto

import java.time.LocalDate

data class StatisticsDTO(
    var dailyStatistics: List<DailyStatisticDTO>,
)

data class DailyStatisticDTO(
    var day: LocalDate,
    var minimumTemperature: Double,
    var averageTemperature: Double,
    var maximumTemperature: Double,
)