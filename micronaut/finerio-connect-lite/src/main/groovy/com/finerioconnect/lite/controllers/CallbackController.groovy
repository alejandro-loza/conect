package com.finerioconnect.lite.controllers

import com.finerioconnect.lite.dtos.ApiListDto
import com.finerioconnect.lite.dtos.CreateCallbackDto
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.UpdateCallbackDto
import com.finerioconnect.lite.services.CallbackService

import javax.inject.Inject
import javax.validation.Valid

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured

@Controller
@Secured('isAuthenticated()')
class CallbackController {

  @Inject
  CallbackService callbackService

  @Post('/callbacks')
  public HttpResponse<CallbackDto> create(
      @Valid @Body CreateCallbackDto createCallbackDto ) {
    return HttpResponse.ok( callbackService.create( createCallbackDto ) ) 
  }

  @Get('/callbacks')
  public HttpResponse<ApiListDto> findAll() {
    return HttpResponse.ok( callbackService.findAll() )
  }

  @Get('/callbacks/{id}')
  public HttpResponse<ApiListDto> findOne( @PathVariable Long id ) {
    return HttpResponse.ok( callbackService.findOne( id ) )
  }

  @Put('/callbacks/{id}')
  public HttpResponse<CallbackDto> update( @PathVariable Long id,
      @Valid @Body UpdateCallbackDto updateCallbackDto ) {

    return HttpResponse.ok( callbackService.update(
        id, updateCallbackDto ) )

  }

  @Delete('/callbacks/{id}')
  public HttpResponse delete( @PathVariable Long id ) {

    callbackService.delete( id )
    return HttpResponse.noContent()

  }

}
