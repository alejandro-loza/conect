package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class CredentialDto {

  String id
  String username
  String status
  Date dateCreated

}
