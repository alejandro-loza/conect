package com.finerioconnect.lite.controllers

import com.finerioconnect.lite.dtos.NotifyCallbackDto

import javax.inject.Inject

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

@Controller
class FinerioConnectCallbacksController {

  @Post('/M7RbGNhCc5dSrHB59K2SEJXPNR3YD3hq4s9BgMARd9Bc8B3TAb')
  public HttpStatus processNotify(
      @Body NotifyCallbackDto notifyCallbackDto ) {
    return HttpStatus.OK
  }

}
