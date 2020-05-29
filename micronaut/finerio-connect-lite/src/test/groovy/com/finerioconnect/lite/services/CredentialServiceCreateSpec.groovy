package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCredentialConnectionDto
import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CreateCustomerDto
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.dtos.CustomerDto
import com.finerioconnect.lite.services.impl.CredentialServiceImpl

import spock.lang.Specification

class CredentialServiceCreateSpec extends Specification {

  def credentialService = new CredentialServiceImpl()

  def credentialConnectionService = Mock( CredentialConnectionService )
  def finerioConnectApiService = Mock( FinerioConnectApiService )
  def userApiDataGormService = Mock( UserApiDataGormService )
  def userService = Mock( UserService )

  def setup() {

    credentialService.credentialConnectionService =
        credentialConnectionService
    credentialService.finerioConnectApiService = finerioConnectApiService
    credentialService.userApiDataGormService = userApiDataGormService
    credentialService.userService = userService

  }

  def 'method worked successfully'() {

    given:
      def createCredentialDto = new CreateCredentialDto(
        customerId: 1,
        bankId: 1,
        username: 'myUsername',
        password: 'myPassword',
        securityCode: 'mySecurityCode'
      )
    when:
      def result = credentialService.create( createCredentialDto )
    then:
      1 * userService.getCurrent() >> new User()
      1 * userApiDataGormService.findByUser( _ as User ) >>
          new UserApiData()
      1 * finerioConnectApiService.createCustomer( _ as UserApiData,
          _ as CreateCustomerDto ) >> new CustomerDto()
      1 * finerioConnectApiService.createCredential( _ as UserApiData,
          _ as CreateCredentialDto ) >> new CredentialDto()
      1 * credentialConnectionService.create(
          _ as CreateCredentialConnectionDto ) >>
          new CredentialConnectionDto()
      result instanceof CredentialDto

  }

  def "parameter 'createCredentialDto' is null"() {

    given:
      def createCredentialDto = null
    when:
      credentialService.create( createCredentialDto )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'credentialService.create.createCredentialDto.null'

  }

}

