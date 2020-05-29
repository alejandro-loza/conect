package com.finerioconnect.lite.services

import com.finerioconnect.lite.dtos.CreateCredentialConnectionHistoryDto
import com.finerioconnect.lite.dtos.CredentialConnectionHistoryDto
import com.finerioconnect.lite.logging.Log

interface CredentialConnectionHistoryService {

  @Log
  CredentialConnectionHistoryDto create(
      CreateCredentialConnectionHistoryDto dto ) throws Exception

}
