package com.finerioconnect.lite.controllers

import com.finerioconnect.lite.dtos.ApiListDto
import com.finerioconnect.lite.dtos.CreateCallbackDto
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.services.CallbackService

import javax.inject.Inject
import javax.validation.Valid

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured

@Controller
@Secured('isAuthenticated()')
class CallbackController {

  @Inject
  CallbackService callbackService

  @Post('/callbacks')
  public HttpResponse<CallbackDto> create(
      @Valid @Body CreateCallbackDto createCallbackDto ) {

    return HttpResponse.ok( callbackService.create(
        createCallbackDto ) ) 

  }

  @Get('/callbacks')
  public HttpResponse<ApiListDto> findAll() {
    return HttpResponse.ok( callbackService.findAll() )
  }

  @Get('/callbacks/{id}')
  public HttpResponse<ApiListDto> findAll( @PathVariable Long id ) {
    return HttpResponse.ok( callbackService.findOne( id ) )
  }

}
