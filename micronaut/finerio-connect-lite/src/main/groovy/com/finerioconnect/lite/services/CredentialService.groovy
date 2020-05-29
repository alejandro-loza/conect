package com.finerioconnect.lite.services

import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CredentialDto
import com.finerioconnect.lite.logging.Log

interface CredentialService {

  @Log
  CredentialDto create( CreateCredentialDto createCredentialDto )
      throws Exception

}
