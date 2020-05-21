package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class CredentialConnectionDto {

  Long id
  Long userId
  String credentialId

}
