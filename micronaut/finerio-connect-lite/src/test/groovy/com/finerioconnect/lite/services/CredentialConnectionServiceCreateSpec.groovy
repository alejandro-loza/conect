package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.CredentialConnection
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.dtos.CreateCredentialConnectionDto
import com.finerioconnect.lite.exceptions.BadRequestException
import com.finerioconnect.lite.services.impl.CredentialConnectionServiceImpl

import spock.lang.Specification

class CredentialConnectionServiceCreateSpec extends Specification {

  def credentialConnectionService = new CredentialConnectionServiceImpl()

  def credentialConnectionGormService =
      Mock( CredentialConnectionGormService )

  def setup() {

    credentialConnectionService.credentialConnectionGormService =
        credentialConnectionGormService

  }

  def 'method worked successfully'() {

    given:
      def createCredentialConnectionDto =
          new CreateCredentialConnectionDto()
    when:
      1 * credentialConnectionGormService.save(
          _ as CredentialConnection ) >> new CredentialConnection(
          user: new User() )
      def result = credentialConnectionService.create(
          createCredentialConnectionDto )
    then:
      result instanceof CredentialConnectionDto

  }

  def "parameter 'createCredentialConnectionDto' is null"() {

    given:
      def createCredentialConnectionDto = null
    when:
      credentialConnectionService.create( createCredentialConnectionDto )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'credentialConnectionService.create' +
          '.createCredentialConnectionDto.null'

  }

}
