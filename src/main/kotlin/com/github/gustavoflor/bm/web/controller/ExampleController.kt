package com.github.gustavoflor.bm.web.controller

import com.github.gustavoflor.bm.web.ApiHeaders
import com.github.gustavoflor.bm.web.dto.ExampleDTO
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime.now

@RestController
@RequestMapping("/v1/example")
class ExampleController(private val meterRegistry: MeterRegistry) {

    @GetMapping
    fun example(@RequestHeader(ApiHeaders.ORIGIN_NAME) originName: String): ExampleDTO {
        if (originName.isBlank()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
        meterRegistry.counter("example_calls_total", "origin_name", originName).count()
        return ExampleDTO(originName, now())
    }

}