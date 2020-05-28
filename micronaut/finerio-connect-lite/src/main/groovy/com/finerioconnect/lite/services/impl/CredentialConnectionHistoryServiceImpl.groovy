package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.CredentialConnectionHistory
import com.finerioconnect.lite.dtos.CreateCredentialConnectionHistoryDto
import com.finerioconnect.lite.dtos.CredentialConnectionHistoryDto
import com.finerioconnect.lite.services.CredentialConnectionGormService
import com.finerioconnect.lite.services.CredentialConnectionHistoryGormService
import com.finerioconnect.lite.services.CredentialConnectionHistoryService

import io.micronaut.spring.tx.annotation.Transactional

import javax.inject.Inject

class CredentialConnectionHistoryServiceImpl
    implements CredentialConnectionHistoryService {

  @Inject
  CredentialConnectionGormService credentialConnectionGormService

  @Inject
  CredentialConnectionHistoryGormService credentialConnectionHistoryGormService

  @Override
  @Transactional
  CredentialConnectionHistoryDto create(
      CreateCredentialConnectionHistoryDto dto ) throws Exception {

    if ( dto == null ) {
      throw new IllegalArgumentException(
          'credentialConnectionHistoryService.create.dto.null' )
    }

    def credentialConnection = credentialConnectionGormService.get(
        dto.credentialConnectionId )
    def credentialConnectionHistory = new CredentialConnectionHistory()
    credentialConnectionHistory.credentialConnection = credentialConnection
    credentialConnectionHistory.stage = dto.stage
    def instance = credentialConnectionHistoryGormService.save(
        credentialConnectionHistory )
    def instanceDto = new CredentialConnectionHistoryDto()
    instanceDto.id = instance.id
    instanceDto.credentialConnectionId = instance.credentialConnection.id
    instanceDto.stage = instance.stage
    instanceDto.dateCreated = instance.dateCreated
    return instanceDto

  }

}
