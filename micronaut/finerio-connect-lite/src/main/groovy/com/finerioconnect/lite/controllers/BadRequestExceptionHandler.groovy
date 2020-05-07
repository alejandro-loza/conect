package com.finerioconnect.lite.controllers

import com.finerioconnect.lite.dtos.Errors
import com.finerioconnect.lite.exceptions.BadRequestException

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler

import javax.inject.Singleton

@Singleton
class BadRequestExceptionHandler
    implements ExceptionHandler<BadRequestException, HttpResponse>{

  @Override
  HttpResponse handle( HttpRequest request, BadRequestException e ) {

    def errors = []
    return HttpResponse.badRequest( errors )

  }

}
