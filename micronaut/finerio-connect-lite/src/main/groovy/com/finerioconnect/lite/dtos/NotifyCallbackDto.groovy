package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class NotifyCallbackDto {

  String credentialId
  String stage

}
