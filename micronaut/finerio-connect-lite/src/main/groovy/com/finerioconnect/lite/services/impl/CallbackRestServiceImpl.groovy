package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.services.CallbackRestService

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient

import javax.inject.Singleton

@Singleton
class CallbackRestServiceImpl implements CallbackRestService {

  @Override
  void post( String url, Object body ) throws Exception {

    HttpClient.create( new URL( url ) ).retrieve(
        HttpRequest.POST( url, body ) )

  }
  
}
