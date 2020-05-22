package com.finerioconnect.lite.services

import com.finerioconnect.lite.logging.Log

interface CallbackRestService {

  @Log
  String post( String url, Object body ) throws Exception
  
}
