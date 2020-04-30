package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.dtos.LoginRequestDto
import com.finerioconnect.lite.dtos.LoginResponseDto
import com.finerioconnect.lite.httpclient.FinerioConnectClient
import com.finerioconnect.lite.services.impl.FinerioConnectApiServiceImpl

import spock.lang.Specification

class FinerioConnectApiServiceCreateCredentialSpec extends Specification {

  def finerioConnectApiService = new FinerioConnectApiServiceImpl()

  def finerioConnectClient = Mock( FinerioConnectClient )

  def setup() {
    finerioConnectApiService.finerioConnectClient = finerioConnectClient
  }

  def 'method worked successfully'() {

    given:
      def userApiData = new UserApiData()
      def createCredentialDto = new CreateCredentialDto()
    when:
      def result = finerioConnectApiService.createCredential(
          userApiData, createCredentialDto )
    then:
      1 * finerioConnectClient.login( _ as String,
          _ as LoginRequestDto ) >> new LoginResponseDto(
          access_token: 'token' )
      1 * finerioConnectClient.createCredential( _ as String,
          _ as CreateCredentialDto ) >> new CredentialDto()
      result instanceof CredentialDto

  }

  def "parameter 'userApiData' is null"() {

    given:
      def userApiData = null
      def createCredentialDto = new CreateCredentialDto()
    when:
      finerioConnectApiService.createCredential( userApiData,
          createCredentialDto )
    then:
      IllegalArgumentException e = thrown()
      e.message ==
          'finerioConnectApiService.createCredential.userApiData.null'

  }

  def "parameter 'createCredentialDto' is null"() {

    given:
      def userApiData = new UserApiData()
      def createCredentialDto = null
    when:
      finerioConnectApiService.createCredential( userApiData,
          createCredentialDto )
    then:
      IllegalArgumentException e = thrown()
      e.message ==
          'finerioConnectApiService.createCredential.createCredentialDto.null'

  }

}
