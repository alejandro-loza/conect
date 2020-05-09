package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.Callback.Nature
import com.finerioconnect.lite.dtos.ApiListDto
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CreateCallbackDto
import com.finerioconnect.lite.exceptions.BadRequestException
import com.finerioconnect.lite.services.CallbackGormService
import com.finerioconnect.lite.services.CallbackService
import com.finerioconnect.lite.services.UserService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallbackServiceImpl implements CallbackService {

  @Inject
  CallbackGormService callbackGormService

  @Inject
  UserService userService

  @Override
  CallbackDto create( CreateCallbackDto createCallbackDto )
      throws Exception {

    if ( createCallbackDto == null ) {
      throw new IllegalArgumentException(
          'callbackService.create.createCallbackDto.null' )
    }

    def user = userService.getCurrent()
    def nature = getNature( createCallbackDto.nature )
    def previousInstance = callbackGormService.findByUserAndNature(
        user, nature )

    if ( previousInstance != null ) {
      throw new BadRequestException( 'callback.exists' )
    }

    def callback = new Callback()
    callback.url = createCallbackDto.url
    callback.nature = nature
    callback.user = user
    def instance = callbackGormService.save( callback )
    return generateCallbackDto( instance )

  }
  
  @Override
  ApiListDto findAll() throws Exception {

    def items = callbackGormService.findByUser( userService.getCurrent() )
    def apiListDto = new ApiListDto()
    def data = []

    for ( item in items ) {
      data << generateCallbackDto( item )
    }

    apiListDto.data = data
    apiListDto.nextCursor = null
    return apiListDto

  }

  @Override
  CallbackDto findOne( Long id ) throws Exception {

    if ( id == null ) {
      throw new IllegalArgumentException(
          'callbackService.findOne.id.null' )
    }

    def callback = callbackGormService.get( id )

    if ( callback?.user != userService.getCurrent() ) {
      return null
    }

    return generateCallbackDto( callback )

  }

  private Nature getNature( String rawNature ) throws Exception {

    try {
      return Nature.valueOf( rawNature )
    } catch ( Exception e ) {
      throw new BadRequestException( 'callback.nature.invalid' )
    }

  }

  private CallbackDto generateCallbackDto( Callback callback )
      throws Exception {

    if ( callback == null ) { return null }
    def callbackDto = new CallbackDto()
    callbackDto.id = callback.id
    callbackDto.url = callback.url
    callbackDto.nature = callback.nature.toString()
    callbackDto.dateCreated = callback.dateCreated
    callbackDto.lastUpdated = callback.lastUpdated
    return callbackDto

  }

}
