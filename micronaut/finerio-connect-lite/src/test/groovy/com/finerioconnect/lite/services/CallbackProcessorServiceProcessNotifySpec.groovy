package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.dtos.NotifyCallbackDto
import com.finerioconnect.lite.services.impl.CallbackProcessorServiceImpl

import spock.lang.Specification

class CallbackProcessorServiceProcessNotifySpec extends Specification {

  def callbackProcessorService = new CallbackProcessorServiceImpl()

  def callbackRestService = Mock( CallbackRestService )
  def callbackService = Mock( CallbackService )
  def credentialConnectionService = Mock( CredentialConnectionService )

  def setup() {

    callbackProcessorService.callbackRestService = callbackRestService
    callbackProcessorService.callbackService = callbackService
    callbackProcessorService.credentialConnectionService =
        credentialConnectionService

  }

  def 'method worked successfully'() {

    given:
      def notifyCallbackDto = new NotifyCallbackDto(
        credentialId: 'credentialId',
        stage: 'start'
      )
    when:
      callbackProcessorService.processNotify( notifyCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          new CredentialConnectionDto( userId: 1L )
      1 * callbackService.findByUserIdAndNature( _ as Long,
          _ as Callback.Nature ) >> new CallbackDto( url: 'url' )
      1 * callbackRestService.post( _ as String, _ as Object ) >>
          'myResponse'
      true

  }

  def 'credentialConnection not found'() {

    given:
      def notifyCallbackDto = new NotifyCallbackDto(
        credentialId: 'credentialId',
        stage: 'start'
      )
    when:
      callbackProcessorService.processNotify( notifyCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          null
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processNotify' +
          '.credentialConnectionDto.not.found'

  }

  def 'callback not found'() {

    given:
      def notifyCallbackDto = new NotifyCallbackDto(
        credentialId: 'credentialId',
        stage: 'start'
      )
    when:
      callbackProcessorService.processNotify( notifyCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          new CredentialConnectionDto( userId: 1L )
      1 * callbackService.findByUserIdAndNature( _ as Long,
          _ as Callback.Nature ) >> null
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processNotify' +
          '.callbackDto.not.found'

  }

  def "parameter 'notifyCallbackDto' is null"() {

    given:
      def notifyCallbackDto = null
    when:
      callbackProcessorService.processNotify( notifyCallbackDto )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processNotify' +
          '.notifyCallbackDto.null'

  }

}

