package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.dtos.AccountDto
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CreateCredentialConnectionHistoryDto
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.dtos.CredentialConnectionHistoryDto
import com.finerioconnect.lite.dtos.AccountsCallbackDto
import com.finerioconnect.lite.services.impl.CallbackProcessorServiceImpl

import spock.lang.Specification

class CallbackProcessorServiceProcessAccountsSpec extends Specification {

  def callbackProcessorService = new CallbackProcessorServiceImpl()

  def callbackRestService = Mock( CallbackRestService )
  def callbackService = Mock( CallbackService )
  def credentialConnectionHistoryService =
      Mock( CredentialConnectionHistoryService )
  def credentialConnectionService = Mock( CredentialConnectionService )

  def setup() {

    callbackProcessorService.callbackRestService = callbackRestService
    callbackProcessorService.callbackService = callbackService
    callbackProcessorService.credentialConnectionHistoryService =
        credentialConnectionHistoryService
    callbackProcessorService.credentialConnectionService =
        credentialConnectionService

  }

  def 'method worked successfully'() {

    given:
      def accountsCallbackDto = new AccountsCallbackDto(
        credentialId: 'credentialId',
        account:  new AccountDto()
      )
    when:
      callbackProcessorService.processAccounts( accountsCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          new CredentialConnectionDto( userId: 1L )
      1 * callbackService.findByUserIdAndNature( _ as Long,
          _ as Callback.Nature ) >> new CallbackDto( url: 'url' )
      1 * callbackRestService.post( _ as String, _ as Object )
      1 * credentialConnectionHistoryService.create(
          _ as CreateCredentialConnectionHistoryDto ) >>
          new CredentialConnectionHistoryDto()
      true

  }

  def 'credentialConnection not found'() {

    given:
      def accountsCallbackDto = new AccountsCallbackDto(
        credentialId: 'credentialId',
        account:  new AccountDto()
      )
    when:
      callbackProcessorService.processAccounts( accountsCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          null
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processAccounts' +
          '.credentialConnectionDto.not.found'

  }

  def 'callback not found'() {

    given:
      def accountsCallbackDto = new AccountsCallbackDto(
        credentialId: 'credentialId',
        account:  new AccountDto()
      )
    when:
      callbackProcessorService.processAccounts( accountsCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          new CredentialConnectionDto( userId: 1L )
      1 * callbackService.findByUserIdAndNature( _ as Long,
          _ as Callback.Nature ) >> null
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processAccounts' +
          '.callbackDto.not.found'

  }

  def "parameter 'accountsCallbackDto' is null"() {

    given:
      def accountsCallbackDto = null
    when:
      callbackProcessorService.processAccounts( accountsCallbackDto )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processAccounts' +
          '.accountsCallbackDto.null'

  }

}

