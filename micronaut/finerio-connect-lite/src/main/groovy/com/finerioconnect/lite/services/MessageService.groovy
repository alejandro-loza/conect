package com.finerioconnect.lite.services

import com.finerioconnect.lite.logging.Log

interface MessageService {

  @Log
  String getMessage( String key ) throws Exception

}
