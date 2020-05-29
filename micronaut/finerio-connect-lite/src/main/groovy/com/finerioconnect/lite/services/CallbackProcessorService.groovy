package com.finerioconnect.lite.services

import com.finerioconnect.lite.dtos.AccountsCallbackDto
import com.finerioconnect.lite.dtos.FailureCallbackDto
import com.finerioconnect.lite.dtos.NotifyCallbackDto
import com.finerioconnect.lite.dtos.SuccessCallbackDto
import com.finerioconnect.lite.dtos.TransactionsCallbackDto
import com.finerioconnect.lite.logging.Log

interface CallbackProcessorService {

  @Log
  void processNotify( NotifyCallbackDto notifyCallbackDto ) throws Exception
  
  @Log
  void processAccounts( AccountsCallbackDto accountsCallbackDto )
      throws Exception
  
  @Log
  void processTransactions(
      TransactionsCallbackDto transactionsCallbackDto ) throws Exception
  
  @Log
  void processSuccess( SuccessCallbackDto successCallbackDto )
      throws Exception
  
  @Log
  void processFailure( FailureCallbackDto failureCallbackDto )
      throws Exception
  
}
