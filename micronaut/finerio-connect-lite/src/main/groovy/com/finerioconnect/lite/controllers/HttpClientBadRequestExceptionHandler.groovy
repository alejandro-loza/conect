package com.finerioconnect.lite.controllers

import com.finerioconnect.lite.dtos.Errors

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.client.exceptions.HttpClientResponseException

import javax.inject.Singleton

@Singleton
class HttpClientBadRequestExceptionHandler
    implements ExceptionHandler<HttpClientResponseException, HttpResponse>{

  @Override
  HttpResponse handle( HttpRequest request,
      HttpClientResponseException e ) {
    return HttpResponse.badRequest( e.response.getBody( Errors ) )
  }

}
