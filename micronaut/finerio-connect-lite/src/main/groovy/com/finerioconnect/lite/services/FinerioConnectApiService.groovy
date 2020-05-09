package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CreateCustomerDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.dtos.CustomerDto
import com.finerioconnect.lite.logging.Log

interface FinerioConnectApiService {

  @Log
  CustomerDto createCustomer( UserApiData userApiData,
      CreateCustomerDto createCustomerDto ) throws Exception

  @Log
  CredentialDto createCredential( UserApiData userApiData,
      CreateCredentialDto createCredentialDto ) throws Exception

}
