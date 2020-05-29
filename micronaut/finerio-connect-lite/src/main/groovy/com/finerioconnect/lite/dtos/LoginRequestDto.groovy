package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class LoginRequestDto {

  String username
  String password
  String grant_type = 'password'

}
