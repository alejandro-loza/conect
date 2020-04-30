package com.finerioconnect.lite.services

import com.finerioconnect.lite.dtos.CreateCredentialDto
import com.finerioconnect.lite.dtos.CredentialDto

interface CredentialService {

  CredentialDto create( CreateCredentialDto createCredentialDto )
      throws Exception

}
