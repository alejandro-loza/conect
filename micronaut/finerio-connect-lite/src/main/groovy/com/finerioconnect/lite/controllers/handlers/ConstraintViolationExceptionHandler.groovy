package com.finerioconnect.lite.controllers.handlers

import com.finerioconnect.lite.dtos.Error
import com.finerioconnect.lite.dtos.Errors
import com.finerioconnect.lite.services.MessageService

import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler

import io.micronaut.validation.exceptions.ConstraintExceptionHandler

import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
@Replaces(ConstraintExceptionHandler)
class ConstraintViolationExceptionHandler
    implements ExceptionHandler<ConstraintViolationException, HttpResponse>{

  @Inject
  MessageService messageService

  @Override
  HttpResponse handle( HttpRequest request,
      ConstraintViolationException e ) {

    def errorsList = []

    for ( constraintViolation in e.constraintViolations ) {

      def error = new Error()
      def message = constraintViolation.message
      error.code = message
      error.title = messageService.getMessage( message )
      error.detail = messageService.getMessage( "${message}.detail" )
      errorsList << error

    }

    def errors = new Errors()
    errors.errors = errorsList
    return HttpResponse.badRequest( errors )

  }

}
