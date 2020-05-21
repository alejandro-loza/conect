package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CreateCredentialConnectionDto
import com.finerioconnect.lite.dtos.CreateCustomerDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.dtos.CustomerDto
import com.finerioconnect.lite.services.CredentialConnectionService
import com.finerioconnect.lite.services.CredentialService
import com.finerioconnect.lite.services.FinerioConnectApiService
import com.finerioconnect.lite.services.UserApiDataGormService
import com.finerioconnect.lite.services.UserService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CredentialServiceImpl implements CredentialService {

  @Inject
  CredentialConnectionService credentialConnectionService

  @Inject
  FinerioConnectApiService finerioConnectApiService

  @Inject
  UserApiDataGormService userApiDataGormService

  @Inject
  UserService userService

  @Override
  CredentialDto create( CreateCredentialDto createCredentialDto )
      throws Exception {

    if ( createCredentialDto == null ) {
      throw new IllegalArgumentException(
          'credentialService.create.createCredentialDto.null' )
    }

    def currentUser = userService.getCurrent()
    def userApiData = userApiDataGormService.findByUser( currentUser )
    def customerDto = getCustomerDto( userApiData )
    createCredentialDto.customerId = customerDto.id
    def credentialDto = finerioConnectApiService.createCredential(
        userApiData, createCredentialDto )
    createCredentialConnection( currentUser, credentialDto.id )
    return credentialDto

  }

  private CustomerDto getCustomerDto( UserApiData userApiData )
      throws Exception {

    def createCustomerDto = new CreateCustomerDto()
    createCustomerDto.name = UUID.randomUUID().toString()
    return finerioConnectApiService.createCustomer( userApiData,
        createCustomerDto )

  }

  private void createCredentialConnection( User currentUser,
      String credentialId ) throws Exception {

    def createCredentialConnectionDto = new CreateCredentialConnectionDto()
    createCredentialConnectionDto.user = currentUser
    createCredentialConnectionDto.credentialId = credentialId
    credentialConnectionService.create( createCredentialConnectionDto )

  }

}

