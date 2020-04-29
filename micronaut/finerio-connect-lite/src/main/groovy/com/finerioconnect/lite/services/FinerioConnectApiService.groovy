package com.finerioconnect.lite.services

import com.finerioconnect.lite.dtos.CreateCustomerDto
import com.finerioconnect.lite.dtos.CustomerDto

interface FinerioConnectApiService {

  CustomerDto createCustomer( CreateCustomerDto createCustomerDto )
      throws Exception
  
}
