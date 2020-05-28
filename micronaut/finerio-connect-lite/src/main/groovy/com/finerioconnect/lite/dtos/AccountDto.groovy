package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class AccountDto {

  String id
  String name
  String number
  BigDecimal balance
  String type

}
