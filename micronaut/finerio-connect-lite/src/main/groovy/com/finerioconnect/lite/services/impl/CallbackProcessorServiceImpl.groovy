package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.dtos.FailureCallbackDto
import com.finerioconnect.lite.dtos.NotifyCallbackDto
import com.finerioconnect.lite.dtos.SuccessCallbackDto
import com.finerioconnect.lite.services.CallbackProcessorService
import com.finerioconnect.lite.services.CallbackRestService
import com.finerioconnect.lite.services.CallbackService
import com.finerioconnect.lite.services.CredentialConnectionService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallbackProcessorServiceImpl implements CallbackProcessorService {

  @Inject
  CallbackRestService callbackRestService

  @Inject
  CallbackService callbackService

  @Inject
  CredentialConnectionService credentialConnectionService

  @Override
  void processNotify( NotifyCallbackDto notifyCallbackDto )
      throws Exception {

    processCallback( notifyCallbackDto, 'processNotify',
        'notifyCallbackDto', Callback.Nature.NOTIFY )

  }
  
  @Override
  void processSuccess( SuccessCallbackDto successCallbackDto )
      throws Exception {

    processCallback( successCallbackDto, 'processSuccess',
        'successCallbackDto', Callback.Nature.SUCCESS )

  }

  @Override
  void processFailure( FailureCallbackDto failureCallbackDto )
      throws Exception {

    processCallback( failureCallbackDto, 'processFailure',
        'failureCallbackDto', Callback.Nature.FAILURE )

  }
  
  private void processCallback( SuccessCallbackDto successCallbackDto,
      String method, String dtoName, Callback.Nature nature )
      throws Exception {

    if ( successCallbackDto == null ) {
      throw new IllegalArgumentException(
          "callbackProcessorService.${method}.${dtoName}.null" )
    }

    def credentialConnectionDto =
        credentialConnectionService.findByCredentialId(
        successCallbackDto.credentialId )

    if ( credentialConnectionDto == null ) {
      throw new IllegalArgumentException(
          "callbackProcessorService.${method}" +
          '.credentialConnectionDto.not.found' )
    }

    def callbackDto = callbackService.findByUserIdAndNature(
        credentialConnectionDto.userId, nature )

    if ( callbackDto == null ) {
      throw new IllegalArgumentException(
          "callbackProcessorService.${method}.callbackDto.not.found" )
    }

    callbackRestService.post( callbackDto.url, successCallbackDto )

  }

}
