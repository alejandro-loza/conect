package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CreateCredentialConnectionHistoryDto
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.dtos.CredentialConnectionHistoryDto
import com.finerioconnect.lite.dtos.FailureCallbackDto
import com.finerioconnect.lite.services.impl.CallbackProcessorServiceImpl

import spock.lang.Specification

class CallbackProcessorServiceProcessFailureSpec extends Specification {

  def callbackProcessorService = new CallbackProcessorServiceImpl()

  def callbackRestService = Mock( CallbackRestService )
  def callbackService = Mock( CallbackService )
  def credentialConnectionHistoryService =
      Mock( CredentialConnectionHistoryService )
  def credentialConnectionService = Mock( CredentialConnectionService )
  def finerioConnectApiService = Mock( FinerioConnectApiService )
  def userService = Mock( UserService )
  def userApiDataGormService = Mock( UserApiDataGormService )

  def setup() {

    callbackProcessorService.callbackRestService = callbackRestService
    callbackProcessorService.callbackService = callbackService
    callbackProcessorService.credentialConnectionHistoryService =
        credentialConnectionHistoryService
    callbackProcessorService.credentialConnectionService =
        credentialConnectionService
    callbackProcessorService.finerioConnectApiService =
        finerioConnectApiService
    callbackProcessorService.userApiDataGormService = userApiDataGormService
    callbackProcessorService.userService = userService

  }

  def 'method worked successfully'() {

    given:
      def failureCallbackDto = new FailureCallbackDto(
        credentialId: 'credentialId',
        message: 'errorMessage',
        code: 'errorCode'
      )
    when:
      callbackProcessorService.processFailure( failureCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          new CredentialConnectionDto( userId: 1L )
      1 * callbackService.findByUserIdAndNature( _ as Long,
          _ as Callback.Nature ) >> new CallbackDto( url: 'url' )
      1 * callbackRestService.post( _ as String, _ as Object )
      1 * credentialConnectionHistoryService.create(
          _ as CreateCredentialConnectionHistoryDto ) >>
          new CredentialConnectionHistoryDto()
      1 * userService.findOne( _ as Long ) >> new User()
      1 * userApiDataGormService.findByUser( _ as User ) >>
          new UserApiData()
      1 * finerioConnectApiService.deleteCredential( _ as UserApiData,
          _ as String )
      true

  }

  def 'credentialConnection not found'() {

    given:
      def failureCallbackDto = new FailureCallbackDto(
        credentialId: 'credentialId',
        message: 'errorMessage',
        code: 'errorCode'
      )
    when:
      callbackProcessorService.processFailure( failureCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          null
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processFailure' +
          '.credentialConnectionDto.not.found'

  }

  def 'callback not found'() {

    given:
      def failureCallbackDto = new FailureCallbackDto(
        credentialId: 'credentialId',
        message: 'errorMessage',
        code: 'errorCode'
      )
    when:
      callbackProcessorService.processFailure( failureCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          new CredentialConnectionDto( userId: 1L )
      1 * callbackService.findByUserIdAndNature( _ as Long,
          _ as Callback.Nature ) >> null
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processFailure' +
          '.callbackDto.not.found'

  }

  def "parameter 'failureCallbackDto' is null"() {

    given:
      def failureCallbackDto = null
    when:
      callbackProcessorService.processFailure( failureCallbackDto )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processFailure' +
          '.failureCallbackDto.null'

  }

}

