package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.Callback.Nature
import com.finerioconnect.lite.dtos.ApiListDto
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CreateCallbackDto
import com.finerioconnect.lite.dtos.UpdateCallbackDto
import com.finerioconnect.lite.exceptions.BadRequestException
import com.finerioconnect.lite.exceptions.ItemNotFoundException
import com.finerioconnect.lite.services.CallbackGormService
import com.finerioconnect.lite.services.CallbackService
import com.finerioconnect.lite.services.UserService

import io.micronaut.spring.tx.annotation.Transactional

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallbackServiceImpl implements CallbackService {

  @Inject
  CallbackGormService callbackGormService

  @Inject
  UserService userService

  @Override
  @Transactional
  CallbackDto create( CreateCallbackDto createCallbackDto )
      throws Exception {

    if ( createCallbackDto == null ) {
      throw new IllegalArgumentException(
          'callbackService.create.createCallbackDto.null' )
    }

    def user = userService.getCurrent()
    def nature = getNature( createCallbackDto.nature )
    def previousInstance =
        callbackGormService.findByUserAndNatureAndDateDeletedIsNull(
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
  @Transactional(readOnly = true)
  ApiListDto findAll() throws Exception {

    def items = callbackGormService.findByUserAndDateDeletedIsNull(
        userService.getCurrent(), [ sort: 'id', order: 'desc' ] )
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
  @Transactional(readOnly = true)
  CallbackDto findOne( Long id ) throws Exception {

    if ( id == null ) {
      throw new IllegalArgumentException(
          'callbackService.findOne.id.null' )
    }

    return generateCallbackDto( findInstance( id ) )

  }

  @Override
  @Transactional
  CallbackDto update( Long id, UpdateCallbackDto updateCallbackDto )
      throws Exception {

    if ( id == null ) {
      throw new IllegalArgumentException(
          'callbackService.update.id.null' )
    }

    if ( updateCallbackDto == null ) {
      throw new IllegalArgumentException(
          'callbackService.update.updateCallbackDto.null' )
    }

    def instance = findInstance( id )
    instance.url = updateCallbackDto.url
    return generateCallbackDto( callbackGormService.save( instance ) )

  }

  @Override
  @Transactional
  void delete( Long id ) throws Exception {

    if ( id == null ) {
      throw new IllegalArgumentException(
          'callbackService.delete.id.null' )
    }

    def instance = findInstance( id )
    instance.dateDeleted = new Date()
    callbackGormService.save( instance )

  }

  @Override
  @Transactional(readOnly = true)
  CallbackDto findByUserIdAndNature( Long userId, Callback.Nature nature )
      throws Exception {

    if ( userId == null ) {
      throw new IllegalArgumentException(
          'callbackService.findByUserIdAndNature.userId.null' )
    }

    if ( nature == null ) {
      throw new IllegalArgumentException(
          'callbackService.findByUserIdAndNature.nature.null' )
    }

    def user = userService.findOne( userId )

    if ( user == null ) {
      throw new IllegalArgumentException(
          'callbackService.findByUserIdAndNature.user.not.found' )
    }

    def callback = callbackGormService.
        findByUserAndNatureAndDateDeletedIsNull( user, nature )
    if ( callback == null ) { return null }
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

  private Callback findInstance( Long id ) throws Exception {

    def callback = callbackGormService.get( id )

    if ( callback == null || callback.dateDeleted != null ||
        callback.user.id != userService.getCurrent().id ) {
      throw new ItemNotFoundException( 'callback.not.found' )
    }

    return callback

  }

}
