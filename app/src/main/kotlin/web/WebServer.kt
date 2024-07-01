package hu.vanio.kotlin.hazifeladat.ms.web

import hu.vanio.kotlin.hazifeladat.ms.service.Calculator
import hu.vanio.kotlin.hazifeladat.ms.service.Downloader
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebServer(var downloader: Downloader, var calculator: Calculator) {
    private val log = LoggerFactory.getLogger(WebServer::class.java)

    @GetMapping("/")
    fun get(model: Model): String {
        val forecastDTO = downloader.download()
        log.debug(forecastDTO.toString())
        model.addAttribute("longitude", forecastDTO.longitude)
        model.addAttribute("latitude", forecastDTO.latitude)

        val statisticsDTO = calculator.calculate(forecastDTO)
        model.addAttribute("hourly", statisticsDTO.dailyStatistics)

        return "index"
    }
}
