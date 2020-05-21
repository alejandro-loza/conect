package com.finerioconnect.lite.services

import com.finerioconnect.lite.dtos.CreateCredentialConnectionDto
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.logging.Log

interface CredentialConnectionService {

  @Log
  CredentialConnectionDto create(
      CreateCredentialConnectionDto createCredentialConnectionDto )
      throws Exception

}
