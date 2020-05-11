package com.finerioconnect.lite.controller.handlers

import com.finerioconnect.lite.dtos.Error
import com.finerioconnect.lite.dtos.Errors
import com.finerioconnect.lite.services.MessageService

import io.micronaut.context.annotation.Replaces
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler

import io.micronaut.http.server.exceptions.ConversionErrorHandler

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Replaces(ConversionErrorHandler)
class ConversionErrorExceptionHandler
    implements ExceptionHandler<ConversionErrorException, HttpResponse>{

  @Inject
  MessageService messageService

  @Override
  HttpResponse handle( HttpRequest request, ConversionErrorException e ) {

    def message = 'url.invalid'
    def error = new Error()
    error.code = message
    error.title = messageService.getMessage( message )
    error.detail = messageService.getMessage( "${message}.detail" )
    def errors = new Errors()
    errors.errors = [ error ]
    return HttpResponse.badRequest( errors )

  }

}
