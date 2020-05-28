package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.CredentialConnectionHistory
import grails.gorm.services.Service

@Service(CredentialConnectionHistory)
interface CredentialConnectionHistoryGormService {

  CredentialConnectionHistory save(
      CredentialConnectionHistory credentialConnectionHistory )

}
