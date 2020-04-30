package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.services.CredentialService
import com.finerioconnect.lite.services.FinerioConnectApiService
import com.finerioconnect.lite.services.UserApiDataService
import com.finerioconnect.lite.services.UserGormService

import io.micronaut.security.utils.SecurityService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CredentialServiceImpl implements CredentialService {

  @Inject
  FinerioConnectApiService finerioConnectApiService

  @Inject
  SecurityService securityService

  @Inject
  UserApiDataService userApiDataService

  @Inject
  UserGormService userGormService

  @Override
  CredentialDto create( CreateCredentialDto createCredentialDto )
      throws Exception {

    if ( createCredentialDto == null ) {
      throw new IllegalArgumentException(
          'credentialService.create.createCredentialDto.null' )
    }

    def username = securityService.username().get()
    def user = userGormService.findByUsername( username )
    def userApiData = userApiDataService.findByUser( user )
    return finerioConnectApiService.createCredential( userApiData,
        createCredentialDto )

  }

}
