package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.CredentialConnection
import com.finerioconnect.lite.dtos.CreateCredentialConnectionDto
import com.finerioconnect.lite.dtos.CredentialConnectionDto
import com.finerioconnect.lite.services.CredentialConnectionGormService
import com.finerioconnect.lite.services.CredentialConnectionService

import io.micronaut.spring.tx.annotation.Transactional

import javax.inject.Inject

class CredentialConnectionServiceImpl
    implements CredentialConnectionService {

  @Inject
  CredentialConnectionGormService credentialConnectionGormService

  @Override
  @Transactional
  CredentialConnectionDto create(
      CreateCredentialConnectionDto createCredentialConnectionDto )
      throws Exception {

    if ( createCredentialConnectionDto == null ) {
      throw new IllegalArgumentException(
          'credentialConnectionService.create' +
          '.createCredentialConnectionDto.null' )
    }

    def credentialConnection = new CredentialConnection()
    credentialConnection.user = createCredentialConnectionDto.user
    credentialConnection.credentialId =
        createCredentialConnectionDto.credentialId
    credentialConnection.customId =
        createCredentialConnectionDto.customId
    def instance = credentialConnectionGormService.save(
        credentialConnection )
    def credentialConnectionDto = new CredentialConnectionDto()
    credentialConnectionDto.id = instance.id
    credentialConnectionDto.userId = instance.user.id
    credentialConnectionDto.credentialId = instance.credentialId
    credentialConnectionDto.customId = instance.customId
    return credentialConnectionDto

  }

  @Override
  @Transactional(readOnly = true)
  CredentialConnectionDto findByCredentialId( String credentialId )
      throws Exception {

    if ( credentialId == null ) {
      throw new IllegalArgumentException(
          'credentialConnectionService.findByCredentialId' +
          '.credentialId.null' )
    }

    def credentialConnection =
        credentialConnectionGormService.findByCredentialId( credentialId )
    if ( credentialConnection == null ) { return null }
    def credentialConnectionDto = new CredentialConnectionDto()
    credentialConnectionDto.id = credentialConnection.id
    credentialConnectionDto.userId = credentialConnection.user.id
    credentialConnectionDto.credentialId = credentialConnection.credentialId
    return credentialConnectionDto


  }

}
