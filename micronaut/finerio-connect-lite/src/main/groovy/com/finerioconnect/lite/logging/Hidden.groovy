package com.finerioconnect.lite.logging

import io.micronaut.context.annotation.Type
import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.Target
import static java.lang.annotation.RetentionPolicy.RUNTIME

@Documented
@Retention(RUNTIME)
@Target([ElementType.TYPE, ElementType.PARAMETER])
@interface Hidden {}
