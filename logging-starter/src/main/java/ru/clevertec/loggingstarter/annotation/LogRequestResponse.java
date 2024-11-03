package ru.clevertec.loggingstarter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для логирования запросов и ответов.
 *
 * <p>
 * Данная аннотация может быть применена как к методам, так и к классам.
 * Если аннотация применяется к классу, то автоматически отслеживаются
 * все методы этого класса.
 * </p>
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface LogRequestResponse {
}
