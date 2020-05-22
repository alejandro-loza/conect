package com.finerioconnect.lite.services

import com.finerioconnect.lite.dtos.NotifyCallbackDto
import com.finerioconnect.lite.logging.Log

interface CallbackProcessorService {

  @Log
  void processNotify( NotifyCallbackDto notifyCallbackDto ) throws Exception
  
}
