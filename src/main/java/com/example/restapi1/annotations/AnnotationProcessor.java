package com.example.restapi1.annotations;

import java.lang.reflect.Method;

public interface AnnotationProcessor {
    void initCounter(String value);
    void process(Method method);
}
