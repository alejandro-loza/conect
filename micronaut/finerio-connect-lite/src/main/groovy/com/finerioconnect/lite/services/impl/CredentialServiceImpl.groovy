package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CreateCustomerDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.dtos.CustomerDto
import com.finerioconnect.lite.services.CredentialService
import com.finerioconnect.lite.services.FinerioConnectApiService
import com.finerioconnect.lite.services.UserApiDataGormService
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
  UserApiDataGormService userApiDataGormService

  @Inject
  UserGormService userGormService

  @Override
  CredentialDto create( CreateCredentialDto createCredentialDto )
      throws Exception {

    if ( createCredentialDto == null ) {
      throw new IllegalArgumentException(
          'credentialService.create.createCredentialDto.null' )
    }

    def userApiData = getUserApiData()
    def customerDto = getCustomerDto( userApiData )
    createCredentialDto.customerId = customerDto.id
    return finerioConnectApiService.createCredential( userApiData,
        createCredentialDto )

  }

  private UserApiData getUserApiData() throws Exception {

    def username = securityService.username().get()
    def user = userGormService.findByUsername( username )
    return userApiDataGormService.findByUser( user )

  }

  private CustomerDto getCustomerDto( UserApiData userApiData )
      throws Exception {

    def createCustomerDto = new CreateCustomerDto()
    createCustomerDto.name = UUID.randomUUID().toString()
    return finerioConnectApiService.createCustomer( userApiData,
        createCustomerDto )

  }

}

