package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class CallbackDto {

  Long id
  String url
  String nature
  Date dateCreated
  Date lastUpdated

}
