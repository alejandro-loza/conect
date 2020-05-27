package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false,
    includeSuperProperties = true)
class TransactionsCallbackDto extends SuccessCallbackDto {

  String accountId
  List<TransactionDto> transactions

}
