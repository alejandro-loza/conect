package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.services.CallbackRestService

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient

import javax.inject.Singleton

@Singleton
class CallbackRestServiceImpl implements CallbackRestService {

  @Override
  String post( String url, Object body ) throws Exception {

   def httpClient = HttpClient.create( new URL( url ) )
   String response = httpClient.toBlocking().retrieve(
       HttpRequest.POST( '' , body ), String )

  }
  
}
