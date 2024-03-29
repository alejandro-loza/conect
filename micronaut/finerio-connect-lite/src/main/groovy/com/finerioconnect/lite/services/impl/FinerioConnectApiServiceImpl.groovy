package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CreateCustomerDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.dtos.CustomerDto
import com.finerioconnect.lite.dtos.LoginRequestDto
import com.finerioconnect.lite.dtos.LoginResponseDto
import com.finerioconnect.lite.httpclient.FinerioConnectClient
import com.finerioconnect.lite.services.FinerioConnectApiService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FinerioConnectApiServiceImpl implements FinerioConnectApiService {

  @Inject
  FinerioConnectClient finerioConnectClient

  @Override
  CustomerDto createCustomer( UserApiData userApiData,
      CreateCustomerDto createCustomerDto )
      throws Exception {

    if ( userApiData == null ) {
      throw new IllegalArgumentException(
          'finerioConnectApiService.createCustomer.userApiData.null' )
    }

    if ( createCustomerDto == null ) {
      throw new IllegalArgumentException(
          'finerioConnectApiService.createCustomer.createCustomerDto.null' )
    }

    return finerioConnectClient.createCustomer(
        getAuthorizationHeader( userApiData ), createCustomerDto.name )

  }
  
  @Override
  CredentialDto createCredential( UserApiData userApiData,
      CreateCredentialDto createCredentialDto ) throws Exception {

    if ( userApiData == null ) {
      throw new IllegalArgumentException(
          'finerioConnectApiService.createCredential.userApiData.null' )
    }

    if ( createCredentialDto == null ) {
      throw new IllegalArgumentException(
          'finerioConnectApiService.createCredential.createCredentialDto.null' )
    }

    return finerioConnectClient.createCredential(
        getAuthorizationHeader( userApiData ), createCredentialDto )

  }

  @Override
  void deleteCredential( UserApiData userApiData, String credentialId )
      throws Exception {

    if ( userApiData == null ) {
      throw new IllegalArgumentException(
          'finerioConnectApiService.deleteCredential.userApiData.null' )
    }

    if ( credentialId == null ) {
      throw new IllegalArgumentException(
          'finerioConnectApiService.deleteCredential.credentialId.null' )
    }

    finerioConnectClient.deleteCredential(
        getAuthorizationHeader( userApiData ), credentialId )

  }

  private String getAuthorizationHeader( UserApiData userApiData )
      throws Exception {
    return "Bearer ${getAccessToken( userApiData )}"
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
