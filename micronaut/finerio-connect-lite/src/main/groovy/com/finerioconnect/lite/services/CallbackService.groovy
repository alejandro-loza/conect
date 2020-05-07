package com.finerioconnect.lite.services

import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CreateCallbackDto

interface CallbackService {

  CallbackDto create( CreateCallbackDto createCallbackDto ) throws Exception
  
}
