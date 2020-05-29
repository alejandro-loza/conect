package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class CustomerDto {

  Long id
  String name
  Date dateCreated

}
