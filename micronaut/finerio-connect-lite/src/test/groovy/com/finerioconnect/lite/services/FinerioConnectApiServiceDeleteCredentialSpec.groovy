package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.LoginRequestDto
import com.finerioconnect.lite.dtos.LoginResponseDto
import com.finerioconnect.lite.httpclient.FinerioConnectClient
import com.finerioconnect.lite.services.impl.FinerioConnectApiServiceImpl

import spock.lang.Specification

class FinerioConnectApiServiceDeleteCredentialSpec extends Specification {

  def finerioConnectApiService = new FinerioConnectApiServiceImpl()

  def finerioConnectClient = Mock( FinerioConnectClient )

  def setup() {
    finerioConnectApiService.finerioConnectClient = finerioConnectClient
  }

  def 'method worked successfully'() {

    given:
      def userApiData = new UserApiData()
      def credentialId = 'credentialId'
    when:
      finerioConnectApiService.deleteCredential( userApiData, credentialId )
    then:
      1 * finerioConnectClient.login( _ as String,
          _ as LoginRequestDto ) >> new LoginResponseDto(
          access_token: 'token' )
      1 * finerioConnectClient.deleteCredential( _ as String,
          _ as String )
      true

  }

  def "parameter 'userApiData' is null"() {

    given:
      def userApiData = null
      def credentialId = 'credentialId'
    when:
      finerioConnectApiService.deleteCredential( userApiData, credentialId )
    then:
      IllegalArgumentException e = thrown()
      e.message ==
          'finerioConnectApiService.deleteCredential.userApiData.null'

  }

  def "parameter 'credentialId' is null"() {

    given:
      def userApiData = new UserApiData()
      def credentialId = null
    when:
      finerioConnectApiService.deleteCredential( userApiData, credentialId )
    then:
      IllegalArgumentException e = thrown()
      e.message ==
          'finerioConnectApiService.deleteCredential.credentialId.null'

  }

}
