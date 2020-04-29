package com.finerioconnect.lite.dtos

import com.finerioconnect.lite.domain.UserApiData

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class CreateCustomerDto {

  UserApiData userApiData
  String name

}
