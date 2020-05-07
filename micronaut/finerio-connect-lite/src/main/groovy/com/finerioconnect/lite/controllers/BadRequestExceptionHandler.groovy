package com.finerioconnect.lite.controllers

import com.finerioconnect.lite.dtos.Error
import com.finerioconnect.lite.dtos.Errors
import com.finerioconnect.lite.exceptions.BadRequestException

import io.micronaut.context.MessageSource
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BadRequestExceptionHandler
    implements ExceptionHandler<BadRequestException, HttpResponse>{

  @Inject
  MessageSource messageSource

  @Override
  HttpResponse handle( HttpRequest request, BadRequestException e ) {

    def error = new Error()
    error.code = e.message
    error.title = getMessage( e.message )
    error.detail = getMessage( "${e.message}.detail" )
    def errors = new Errors()
    errors.errors = [ error ]
    return HttpResponse.badRequest( errors )

  }

  private String getMessage( String key ) throws Exception {

    def context = MessageSource.MessageContext.DEFAULT
    return messageSource.getMessage( key, context, key )

  }

}
