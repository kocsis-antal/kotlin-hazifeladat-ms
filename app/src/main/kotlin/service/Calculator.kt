package hu.vanio.kotlin.hazifeladat.ms.service

import hu.vanio.kotlin.hazifeladat.ms.dto.DailyStatisticDTO
import hu.vanio.kotlin.hazifeladat.ms.dto.ForecastDTO
import hu.vanio.kotlin.hazifeladat.ms.dto.StatisticsDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class Calculator {
    private val log = LoggerFactory.getLogger(Calculator::class.java)

    fun calculate(forecast: ForecastDTO): StatisticsDTO = StatisticsDTO(forecast.hourly.getDates().map { day ->
        val temperatures = forecast.hourly.temperature_2m.filterIndexed { index, value ->
            forecast.hourly.time.get(index).toLocalDate() == day
        }
        val avg = temperatures.average()
        val min = temperatures.min()
        val max = temperatures.max()
        log.debug("Day: $day, min: $min, avg: $avg, max: $max")

        DailyStatisticDTO(day, min, avg, max)
    })
}