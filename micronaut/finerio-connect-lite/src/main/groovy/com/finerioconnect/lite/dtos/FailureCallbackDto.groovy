package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false,
    includeSuperProperties = true)
class FailureCallbackDto extends SuccessCallbackDto {

  String message
  String code

}
