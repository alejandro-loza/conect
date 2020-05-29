package com.finerioconnect.lite.controllers.handlers

import com.finerioconnect.lite.dtos.Error
import com.finerioconnect.lite.dtos.Errors
import com.finerioconnect.lite.exceptions.BadRequestException
import com.finerioconnect.lite.services.MessageService

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BadRequestExceptionHandler
    implements ExceptionHandler<BadRequestException, HttpResponse>{

  @Inject
  MessageService messageService

  @Override
  HttpResponse handle( HttpRequest request, BadRequestException e ) {

    def error = new Error()
    error.code = e.message
    error.title = messageService.getMessage( e.message )
    error.detail = messageService.getMessage( "${e.message}.detail" )
    def errors = new Errors()
    errors.errors = [ error ]
    return HttpResponse.badRequest( errors )

  }

}
