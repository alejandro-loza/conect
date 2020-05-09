package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.UpdateCallbackDto
import com.finerioconnect.lite.exceptions.ItemNotFoundException
import com.finerioconnect.lite.services.impl.CallbackServiceImpl

import spock.lang.Specification

class CallbackServiceUpdateSpec extends Specification {

  def callbackService = new CallbackServiceImpl()

  def callbackGormService = Mock( CallbackGormService )
  def userService = Mock( UserService )

  def setup() {

    callbackService.callbackGormService = callbackGormService
    callbackService.userService = userService

  }

  def 'method worked successfully'() {

    given:
      def newUrl = 'newUrl'
      def id = 1L
      def updateCallbackDto = new UpdateCallbackDto( url: newUrl )
    when:
      1 * userService.getCurrent() >> new User()
      1 * callbackGormService.get( _ as Long ) >>
          new Callback( user: new User() )
      1 * callbackGormService.save( _ as Callback ) >>
          new Callback( url: newUrl )
      def result = callbackService.update( id, updateCallbackDto )
    then:
      result instanceof CallbackDto
      result.url == newUrl

  }

  def 'instance not found'() {

    given:
      def id = 1L
      def updateCallbackDto = new UpdateCallbackDto()
    when:
      1 * callbackGormService.get( _ as Long ) >> null
      def result = callbackService.update( id, updateCallbackDto )
    then:
      ItemNotFoundException e = thrown()
      e.message == 'callback.not.found'

  }

  def 'instance from different user'() {

    given:
      def id = 1L
      def updateCallbackDto = new UpdateCallbackDto()
    when:
      1 * userService.getCurrent() >> new User( id: 1L )
      1 * callbackGormService.get( _ as Long ) >>
          new Callback( user: new User( id: 2L ) )
      def result = callbackService.update( id, updateCallbackDto )
    then:
      ItemNotFoundException e = thrown()
      e.message == 'callback.not.found'

  }

  def "parameter 'id' is null"() {

    given:
      def id = null
      def updateCallbackDto = new UpdateCallbackDto()
    when:
      callbackService.update( id, updateCallbackDto )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'callbackService.update.id.null'

  }

  def "parameter 'updateCallbackDto' is null"() {

    given:
      def id = 1L
      def updateCallbackDto = null
    when:
      callbackService.update( id, updateCallbackDto )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'callbackService.update.updateCallbackDto.null'

  }

}
