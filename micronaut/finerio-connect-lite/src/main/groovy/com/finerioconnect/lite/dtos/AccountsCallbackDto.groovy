package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false,
    includeSuperProperties = true)
class AccountsCallbackDto extends SuccessCallbackDto {

  AccountDto account

}
