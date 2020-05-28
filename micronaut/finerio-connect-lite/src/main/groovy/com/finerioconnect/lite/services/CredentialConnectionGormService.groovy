package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.CredentialConnection
import grails.gorm.services.Service

@Service(CredentialConnection) 
interface CredentialConnectionGormService {

  CredentialConnection save( CredentialConnection credentialConnection )

  CredentialConnection findByCredentialId( String credentialId )

  CredentialConnection get( Serializable id )

}
