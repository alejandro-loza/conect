package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class CreateCredentialDto {

  String customId
  Long bankId
  String username
  String password
  String securityCode
  Long customerId

}
