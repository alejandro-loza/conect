package com.finerioconnect.lite.controllers

import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.services.CredentialService

import javax.inject.Inject

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured

@Controller
@Secured('isAuthenticated()')
class CredentialController {

  @Inject
  CredentialService credentialService

  @Post('/credentials')
  public HttpResponse<CredentialDto> create(
      @Body CreateCredentialDto createCredentialDto ) {

    return HttpResponse.ok( credentialService.create(
        createCredentialDto ) ) 

  }

}
