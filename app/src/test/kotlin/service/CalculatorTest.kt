package service

import hu.vanio.kotlin.hazifeladat.ms.dto.ForecastDTO
import hu.vanio.kotlin.hazifeladat.ms.dto.HourlyDTO
import hu.vanio.kotlin.hazifeladat.ms.dto.HourlyUnitsDTO
import hu.vanio.kotlin.hazifeladat.ms.service.Calculator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CalculatorTest {
    private val calculator: Calculator = Calculator()

    @Test
    fun `test calculate statistics`() {
        // Given
        val forecast = createMockForecast()

        // When
        val statistics = calculator.calculate(forecast)

        // Then
        assertEquals(2, statistics.dailyStatistics.size) // Ensure correct number of days

        // Verify for the first day
        val firstDayStats = statistics.dailyStatistics[0]
        assertEquals(forecast.hourly.time[0].toLocalDate(), firstDayStats.day)
        assertEquals(20.0, firstDayStats.minimumTemperature)
        assertEquals(21.0, firstDayStats.averageTemperature)
        assertEquals(22.0, firstDayStats.maximumTemperature)

        // Verify for the second day
        val secondDayStats = statistics.dailyStatistics[1]
        assertEquals(forecast.hourly.time[3].toLocalDate(), secondDayStats.day)
        assertEquals(18.0, secondDayStats.minimumTemperature)
        assertEquals(19.0, secondDayStats.averageTemperature)
        assertEquals(20.0, secondDayStats.maximumTemperature)
    }

    // Helper function to create a mock ForecastDTO for testing
    private fun createMockForecast(): ForecastDTO {
        val time = listOf(
            LocalDateTime.of(2024, 6, 1, 12, 0),
            LocalDateTime.of(2024, 6, 1, 15, 0),
            LocalDateTime.of(2024, 6, 1, 18, 0),
            LocalDateTime.of(2024, 6, 2, 12, 0),
            LocalDateTime.of(2024, 6, 2, 15, 0),
            LocalDateTime.of(2024, 6, 2, 18, 0),
        )

        val temperatures = listOf(
            20.0, 21.0, 22.0, 20.0, 19.0, 18.0
        )

        return ForecastDTO(
            latitude = 47.4984,
            longitude = 19.0404,
            generationtime_ms = 0,
            utc_offset_seconds = 0,
            timezone = "Europe/Budapest",
            timezone_abbreviation = "CEST",
            elevation = 0.0,
            hourly_units = HourlyUnitsDTO("iso8601", "°C"),
            hourly = HourlyDTO(time, temperatures)
        )
    }
}