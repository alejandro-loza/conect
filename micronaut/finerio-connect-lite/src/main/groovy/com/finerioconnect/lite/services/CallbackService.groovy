package com.finerioconnect.lite.services

import com.finerioconnect.lite.dtos.ApiListDto
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CreateCallbackDto
import com.finerioconnect.lite.logging.Log

interface CallbackService {

  @Log
  CallbackDto create( CreateCallbackDto createCallbackDto ) throws Exception
  
  @Log
  ApiListDto findAll() throws Exception

}
