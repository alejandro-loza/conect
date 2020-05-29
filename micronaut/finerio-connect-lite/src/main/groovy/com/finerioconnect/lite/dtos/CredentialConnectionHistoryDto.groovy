package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class CredentialConnectionHistoryDto {

  Long id
  Long credentialConnectionId
  String stage
  Date dateCreated

}
