package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCustomerDto
import com.finerioconnect.lite.dtos.CustomerDto
import com.finerioconnect.lite.dtos.LoginRequestDto
import com.finerioconnect.lite.dtos.LoginResponseDto
import com.finerioconnect.lite.httpclient.FinerioConnectClient
import com.finerioconnect.lite.services.FinerioConnectApiService

import javax.inject.Inject
import javax.inject.Singleton

import io.micronaut.security.utils.SecurityService

@Singleton
class FinerioConnectApiServiceImpl implements FinerioConnectApiService {

  @Inject
  FinerioConnectClient finerioConnectClient

  @Override
  CustomerDto createCustomer( CreateCustomerDto createCustomerDto )
      throws Exception {

    def userApiData = createCustomerDto.userApiData
    def authorizationHeader = "Bearer ${getAccessToken( userApiData )}"
    return finerioConnectClient.createCustomer(
        authorizationHeader, createCustomerDto.name )

  }
  
  private String getAccessToken( UserApiData userApiData )
      throws Exception {

    def authEncoded = "${userApiData.clientId}:${userApiData.clientSecret}"
        .bytes.encodeBase64().toString() 
    def authorizationHeader = "Basic ${authEncoded}"
    def loginRequestDto = new LoginRequestDto()
    loginRequestDto.username = userApiData.username
    loginRequestDto.password = userApiData.password
    def loginResponse = finerioConnectClient.login( authorizationHeader,
        loginRequestDto )
    return loginResponse.access_token

  }

}
