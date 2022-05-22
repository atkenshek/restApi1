package com.example.restapi1.annotations.impl;

import com.example.restapi1.annotations.AnnotationProcessor;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MetricAnnotationProcessor implements AnnotationProcessor {
    private Counter counter;
    private MeterRegistry meterRegistry;

    public MetricAnnotationProcessor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void initCounter(String value) {
        this.counter = meterRegistry.counter("app.restApi1.", value);
    }

    @Override
    public void process(Method method) {
        this.counter.increment();
    }
}
