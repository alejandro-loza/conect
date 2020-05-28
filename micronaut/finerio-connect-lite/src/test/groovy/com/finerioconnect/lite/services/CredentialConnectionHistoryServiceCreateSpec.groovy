package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.CredentialConnection
import com.finerioconnect.lite.domain.CredentialConnectionHistory
import com.finerioconnect.lite.dtos.CreateCredentialConnectionHistoryDto
import com.finerioconnect.lite.dtos.CredentialConnectionHistoryDto
import com.finerioconnect.lite.services.impl.CredentialConnectionHistoryServiceImpl

import spock.lang.Specification

class CredentialConnectionHistoryServiceCreateSpec extends Specification {

  def credentialConnectionHistoryService =
      new CredentialConnectionHistoryServiceImpl()

  def credentialConnectionGormService = Mock(
      CredentialConnectionGormService )
  def credentialConnectionHistoryGormService = Mock(
      CredentialConnectionHistoryGormService )

  def setup() {

    credentialConnectionHistoryService.credentialConnectionGormService =
            credentialConnectionGormService
    credentialConnectionHistoryService.
        credentialConnectionHistoryGormService =
            credentialConnectionHistoryGormService

  }

  def 'method worked successfully'() {

    given:
      def dto = new CreateCredentialConnectionHistoryDto(
        credentialConnectionId: 1L,
        stage: 'myStage' 
      )
      def credentialConnection = new CredentialConnection()
    when:
      def result = credentialConnectionHistoryService.create( dto )
    then:
      1 * credentialConnectionGormService.get( _ as Long ) >>
          credentialConnection
      1 * credentialConnectionHistoryGormService.save(
          _ as CredentialConnectionHistory ) >>
          new CredentialConnectionHistory(
          credentialConnection: credentialConnection )
      result instanceof CredentialConnectionHistoryDto

  }

  def "parameter 'dto' is null"() {

    given:
      def dto = null
    when:
      credentialConnectionHistoryService.create( dto )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'credentialConnectionHistoryService.create.dto.null'

  }

}

