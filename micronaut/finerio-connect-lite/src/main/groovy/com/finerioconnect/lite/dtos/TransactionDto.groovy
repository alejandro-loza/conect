package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class TransactionDto {

  String description
  BigDecimal amount
  Boolean isCharge
  Date date
  BigDecimal balance

}
