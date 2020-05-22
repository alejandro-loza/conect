package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.CredentialConnection
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.services.impl.CredentialConnectionServiceImpl

import spock.lang.Specification

class CredentialConnectionServiceFindByCredentialIdSpec
    extends Specification {

  def credentialConnectionService = new CredentialConnectionServiceImpl()

  def credentialConnectionGormService =
      Mock( CredentialConnectionGormService )

  def setup() {

    credentialConnectionService.credentialConnectionGormService =
        credentialConnectionGormService

  }

  def 'method worked successfully'() {

    given:
      def credentialId = 'credentialId'
    when:
      def result = credentialConnectionService.findByCredentialId(
          credentialId )
    then:
      1 * credentialConnectionGormService.findByCredentialId(
          _ as String ) >> new CredentialConnection( user: new User() )
      result instanceof CredentialConnectionDto

  }

  def 'instance not found'() {

    given:
      def credentialId = 'credentialId'
    when:
      def result = credentialConnectionService.findByCredentialId(
          credentialId )
    then:
      1 * credentialConnectionGormService.findByCredentialId(
          _ as String ) >> null
      result == null

  }

  def "parameter 'credentialId' is null"() {

    given:
      def credentialId = null
    when:
      credentialConnectionService.findByCredentialId( credentialId )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'credentialConnectionService.findByCredentialId' +
          '.credentialId.null'

  }

}

