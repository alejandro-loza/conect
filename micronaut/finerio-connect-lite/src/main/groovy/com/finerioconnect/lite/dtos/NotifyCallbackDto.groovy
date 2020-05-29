package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false,
    includeSuperProperties = true)
class NotifyCallbackDto extends SuccessCallbackDto {

  String stage

}
