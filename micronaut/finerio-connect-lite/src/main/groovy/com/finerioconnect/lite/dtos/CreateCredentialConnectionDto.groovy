package com.finerioconnect.lite.dtos

import com.finerioconnect.lite.domain.User

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class CreateCredentialConnectionDto {

  User user
  String credentialId

}
