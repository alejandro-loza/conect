package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CreateCustomerDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.dtos.CustomerDto
import com.finerioconnect.lite.services.impl.CredentialServiceImpl

import io.micronaut.security.utils.SecurityService

import spock.lang.Specification

class CredentialServiceCreateSpec extends Specification {

  def credentialService = new CredentialServiceImpl()

  def finerioConnectApiService = Mock( FinerioConnectApiService )
  def securityService = Mock( SecurityService )
  def userApiDataGormService = Mock( UserApiDataGormService )
  def userGormService = Mock( UserGormService )

  def setup() {

    credentialService.finerioConnectApiService = finerioConnectApiService
    credentialService.securityService = securityService
    credentialService.userApiDataGormService = userApiDataGormService
    credentialService.userGormService = userGormService

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
      1 * securityService.username() >> Optional.of( 'username' )
      1 * userGormService.findByUsername( _ as String ) >> new User()
      1 * userApiDataGormService.findByUser( _ as User ) >>
          new UserApiData()
      1 * finerioConnectApiService.createCustomer( _ as UserApiData,
          _ as CreateCustomerDto ) >> new CustomerDto()
      1 * finerioConnectApiService.createCredential( _ as UserApiData,
          _ as CreateCredentialDto ) >> new CredentialDto()
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

