package com.github.gustavoflor.bm.config

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Metrics
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MeterRegistryConfig {

    @Bean
    @ConditionalOnClass(name = ["io.opentelemetry.javaagent.OpenTelemetryAgent"])
    fun meterRegistry(): MeterRegistry? {
        val openTelemetryMeterRegistry = Metrics.globalRegistry
            .registries
            .stream()
            .filter { it.javaClass.name.contains("OpenTelemetryMeterRegistry") }
            .findAny()
        openTelemetryMeterRegistry.ifPresent(Metrics.globalRegistry::remove)
        return openTelemetryMeterRegistry.orElse(null)
    }

}