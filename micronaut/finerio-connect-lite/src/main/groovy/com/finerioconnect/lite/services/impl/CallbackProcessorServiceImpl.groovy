package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.dtos.AccountsCallbackDto
import com.finerioconnect.lite.dtos.CreateCredentialConnectionHistoryDto
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.dtos.FailureCallbackDto
import com.finerioconnect.lite.dtos.NotifyCallbackDto
import com.finerioconnect.lite.dtos.SuccessCallbackDto
import com.finerioconnect.lite.dtos.TransactionsCallbackDto
import com.finerioconnect.lite.services.CallbackProcessorService
import com.finerioconnect.lite.services.CallbackRestService
import com.finerioconnect.lite.services.CallbackService
import com.finerioconnect.lite.services.CredentialConnectionHistoryService
import com.finerioconnect.lite.services.CredentialConnectionService
import com.finerioconnect.lite.services.FinerioConnectApiService
import com.finerioconnect.lite.services.UserApiDataGormService
import com.finerioconnect.lite.services.UserService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallbackProcessorServiceImpl implements CallbackProcessorService {

  @Inject
  CallbackRestService callbackRestService

  @Inject
  CallbackService callbackService

  @Inject
  CredentialConnectionHistoryService credentialConnectionHistoryService

  @Inject
  CredentialConnectionService credentialConnectionService

  @Inject
  FinerioConnectApiService finerioConnectApiService

  @Inject
  UserApiDataGormService userApiDataGormService

  @Inject
  UserService userService

  @Override
  void processNotify( NotifyCallbackDto notifyCallbackDto )
      throws Exception {

    processCallback( notifyCallbackDto, 'processNotify',
        'notifyCallbackDto', Callback.Nature.NOTIFY )

  }
  
  @Override
  void processAccounts( AccountsCallbackDto accountsCallbackDto )
      throws Exception {

    processCallback( accountsCallbackDto, 'processAccounts',
        'accountsCallbackDto', Callback.Nature.ACCOUNTS )

  }
  
  @Override
  void processTransactions(
      TransactionsCallbackDto transactionsCallbackDto ) throws Exception {

    processCallback( transactionsCallbackDto, 'processTransactions',
        'transactionsCallbackDto', Callback.Nature.TRANSACTIONS )

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
    postProcessCallback( credentialConnectionDto, nature,
        successCallbackDto )

  }

  private void postProcessCallback(
      CredentialConnectionDto credentialConnectionDto,
      Callback.Nature nature, SuccessCallbackDto successCallbackDto )
      throws Exception {

    createCredentialConnectionHistory( credentialConnectionDto.id, nature,
        successCallbackDto )

    if ( nature == Callback.Nature.SUCCESS ||
        nature == Callback.Nature.FAILURE ) {
      deleteCredential( successCallbackDto.credentialId,
          credentialConnectionDto.userId )
    }

  }

  private void createCredentialConnectionHistory(
      Long credentialConnectionId, Callback.Nature nature,
      SuccessCallbackDto successCallbackDto ) throws Exception {

    CreateCredentialConnectionHistoryDto dto =
        new CreateCredentialConnectionHistoryDto()
    dto.credentialConnectionId = credentialConnectionId
    dto.stage = nature.toString().toLowerCase() +
        ( successCallbackDto instanceof NotifyCallbackDto ?
        "_${(successCallbackDto as NotifyCallbackDto).stage.toLowerCase()}" :
        '' )
    credentialConnectionHistoryService.create( dto )

  }

  private void deleteCredential( String credentialId, Long userId )
      throws Exception {

    def user = userService.findOne( userId )
    def userApiData = userApiDataGormService.findByUser( user )
    finerioConnectApiService.deleteCredential( userApiData, credentialId )

  }

}
