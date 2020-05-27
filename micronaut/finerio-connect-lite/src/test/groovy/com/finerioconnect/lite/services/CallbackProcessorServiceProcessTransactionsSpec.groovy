package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.dtos.TransactionDto
import com.finerioconnect.lite.dtos.TransactionsCallbackDto
import com.finerioconnect.lite.services.impl.CallbackProcessorServiceImpl

import spock.lang.Specification

class CallbackProcessorServiceProcessTransactionsSpec extends Specification {

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
      def transactionsCallbackDto = new TransactionsCallbackDto(
        credentialId: 'credentialId',
        accountId: 'accountId',
        transactions: [ new TransactionDto(), new TransactionDto() ]
      )
    when:
      callbackProcessorService.processTransactions(
          transactionsCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          new CredentialConnectionDto( userId: 1L )
      1 * callbackService.findByUserIdAndNature( _ as Long,
          _ as Callback.Nature ) >> new CallbackDto( url: 'url' )
      1 * callbackRestService.post( _ as String, _ as Object )
      true

  }

  def 'credentialConnection not found'() {

    given:
      def transactionsCallbackDto = new TransactionsCallbackDto(
        credentialId: 'credentialId',
        accountId: 'accountId',
        transactions: [ new TransactionDto(), new TransactionDto() ]
      )
    when:
      callbackProcessorService.processTransactions(
          transactionsCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          null
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processTransactions' +
          '.credentialConnectionDto.not.found'

  }

  def 'callback not found'() {

    given:
      def transactionsCallbackDto = new TransactionsCallbackDto(
        credentialId: 'credentialId',
        accountId: 'accountId',
        transactions: [ new TransactionDto(), new TransactionDto() ]
      )
    when:
      callbackProcessorService.processTransactions(
          transactionsCallbackDto )
    then:
      1 * credentialConnectionService.findByCredentialId( _ as String ) >>
          new CredentialConnectionDto( userId: 1L )
      1 * callbackService.findByUserIdAndNature( _ as Long,
          _ as Callback.Nature ) >> null
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processTransactions' +
          '.callbackDto.not.found'

  }

  def "parameter 'transactionsCallbackDto' is null"() {

    given:
      def transactionsCallbackDto = null
    when:
      callbackProcessorService.processTransactions(
          transactionsCallbackDto )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'callbackProcessorService.processTransactions' +
          '.transactionsCallbackDto.null'

  }

}

