package com.finerioconnect.lite.controllers.handlers

import com.finerioconnect.lite.exceptions.ItemNotFoundException

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler

import javax.inject.Singleton

@Singleton
class ItemNotFoundExceptionHandler
    implements ExceptionHandler<ItemNotFoundException, HttpResponse>{

  @Override
  HttpResponse handle( HttpRequest request, ItemNotFoundException e ) {
    return HttpResponse.notFound()
  }

}
