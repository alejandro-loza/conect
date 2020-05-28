package com.finerioconnect.lite.controllers

import com.finerioconnect.lite.dtos.AccountsCallbackDto
import com.finerioconnect.lite.dtos.FailureCallbackDto
import com.finerioconnect.lite.dtos.NotifyCallbackDto
import com.finerioconnect.lite.dtos.SuccessCallbackDto
import com.finerioconnect.lite.dtos.TransactionsCallbackDto
import com.finerioconnect.lite.services.CallbackProcessorService

import javax.annotation.security.PermitAll
import javax.inject.Inject

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured

@Controller
@PermitAll
class FinerioConnectCallbacksController {

  @Inject
  CallbackProcessorService callbackProcessorService

  @Post('/M7RbGNhCc5dSrHB59K2SEJXPNR3YD3hq4s9BgMARd9Bc8B3TAb')
  public HttpStatus processNotify(
      @Body NotifyCallbackDto notifyCallbackDto ) {

    callbackProcessorService.processNotify( notifyCallbackDto )
    return HttpStatus.OK

  }

  @Post('/WQfBPSCTTPRQupJV8a5csvRfcgLyUC9D9aByZzUymputrUsk9b')
  public HttpStatus processAccounts(
      @Body AccountsCallbackDto accountsCallbackDto ) {

    callbackProcessorService.processAccounts( accountsCallbackDto )
    return HttpStatus.OK

  }

  @Post('/6zzzCnjxtYQwgxXN2WcKgwT6FZBtEDuSMYxQEWf3GfumTR99Ax')
  public HttpStatus processTransactions(
      @Body TransactionsCallbackDto transactionsCallbackDto ) {

    callbackProcessorService.processTransactions( transactionsCallbackDto )
    return HttpStatus.OK

  }

  @Post('/3dr5bJ72fDZ8syMNkSPMzF3eMUZ3fQ7N6EVvkczJbpLT4rBUQg')
  public HttpStatus processSuccess(
      @Body SuccessCallbackDto successCallbackDto ) {

    callbackProcessorService.processSuccess( successCallbackDto )
    return HttpStatus.OK

  }

  @Post('/B9tVrjPA5qbE8KH8RsjPru3DPJHR7AbncrNaNJ3FDj5mu8nLXp')
  public HttpStatus processFailure(
      @Body FailureCallbackDto failureCallbackDto ) {

    callbackProcessorService.processFailure( failureCallbackDto )
    return HttpStatus.OK

  }

}
