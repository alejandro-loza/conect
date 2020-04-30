package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCustomerDto
import com.finerioconnect.lite.dtos.CustomerDto
import com.finerioconnect.lite.dtos.LoginRequestDto
import com.finerioconnect.lite.dtos.LoginResponseDto
import com.finerioconnect.lite.httpclient.FinerioConnectClient
import com.finerioconnect.lite.services.impl.FinerioConnectApiServiceImpl

import spock.lang.Specification

class FinerioConnectApiServiceCreateCustomerSpec extends Specification {

  def finerioConnectApiService = new FinerioConnectApiServiceImpl()

  def finerioConnectClient = Mock( FinerioConnectClient )

  def setup() {
    finerioConnectApiService.finerioConnectClient = finerioConnectClient
  }

  def 'method worked successfully'() {

    given:
      def userApiData = new UserApiData()
      def createCustomerDto = new CreateCustomerDto( name: 'name' )
    when:
      def result = finerioConnectApiService.createCustomer(
          userApiData, createCustomerDto )
    then:
      1 * finerioConnectClient.login( _ as String,
          _ as LoginRequestDto ) >> new LoginResponseDto(
          access_token: 'token' )
      1 * finerioConnectClient.createCustomer( _ as String,
          _ as String ) >> new CustomerDto()
      result instanceof CustomerDto

  }

  def "parameter 'userApiData' is null"() {

    given:
      def userApiData = null
      def createCustomerDto = new CreateCustomerDto( name: 'name' )
    when:
      finerioConnectApiService.createCustomer( userApiData,
          createCustomerDto )
    then:
      IllegalArgumentException e = thrown()
      e.message ==
          'finerioConnectApiService.createCustomer.userApiData.null'

  }

  def "parameter 'createCustomerDto' is null"() {

    given:
      def userApiData = new UserApiData()
      def createCustomerDto = null
    when:
      finerioConnectApiService.createCustomer( userApiData,
          createCustomerDto )
    then:
      IllegalArgumentException e = thrown()
      e.message ==
          'finerioConnectApiService.createCustomer.createCustomerDto.null'

  }

}
