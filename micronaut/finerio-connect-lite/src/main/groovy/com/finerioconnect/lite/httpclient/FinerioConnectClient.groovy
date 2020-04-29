package com.finerioconnect.lite.httpclient

import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.dtos.CustomerDto
import com.finerioconnect.lite.dtos.LoginRequestDto
import com.finerioconnect.lite.dtos.LoginResponseDto

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

import io.reactivex.Single

@Client("\${finerioconnect.api.url}")
interface FinerioConnectClient {

  @Post(value = '/oauth/token',
    produces = [ MediaType.APPLICATION_FORM_URLENCODED ])
  LoginResponseDto login(
      @Header String authorization,
      @Body LoginRequestDto logiRequestDto )

  @Post('/customers')
  CustomerDto createCustomer( @Header String authorization, String name )

  @Post('/credentials')
  CredentialDto createCredential( @Header String authorization,
      @Body CreateCredentialDto createCredentialDto )

}
