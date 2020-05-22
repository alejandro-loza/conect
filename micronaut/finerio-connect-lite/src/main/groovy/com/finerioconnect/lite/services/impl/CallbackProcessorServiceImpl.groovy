package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.dtos.NotifyCallbackDto
import com.finerioconnect.lite.services.CallbackProcessorService
import com.finerioconnect.lite.services.CallbackRestService
import com.finerioconnect.lite.services.CallbackService
import com.finerioconnect.lite.services.CredentialConnectionService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallbackProcessorServiceImpl implements CallbackProcessorService {

  @Singleton
  CallbackRestService callbackRestService

  @Singleton
  CallbackService callbackService

  @Singleton
  CredentialConnectionService credentialConnectionService

  @Override
  void processNotify( NotifyCallbackDto notifyCallbackDto )
      throws Exception {

    if ( notifyCallbackDto == null ) {
      throw new IllegalArgumentException(
          'callbackProcessorService.processNotify.notifyCallbackDto.null' )
    }

    def credentialConnectionDto =
        credentialConnectionService.findByCredentialId(
            notifyCallbackDto.credentialId )

    if ( credentialConnectionDto == null ) {
      throw new IllegalArgumentException(
          'callbackProcessorService.processNotify' +
          '.credentialConnectionDto.not.found' )
    }

    def callbackDto = callbackService.findByUserIdAndNature(
        credentialConnectionDto.userId, Callback.Nature.NOTIFY )

    if ( callbackDto == null ) {
      throw new IllegalArgumentException(
          'callbackProcessorService.processNotify.callbackDto.not.found' )
    }

    callbackRestService.post( callbackDto.url, notifyCallbackDto )

  }
  
}
