package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.exceptions.ItemNotFoundException
import com.finerioconnect.lite.services.impl.CallbackServiceImpl

import spock.lang.Specification

class CallbackServiceFindOneSpec extends Specification {

  def callbackService = new CallbackServiceImpl()

  def callbackGormService = Mock( CallbackGormService )
  def userService = Mock( UserService )

  def setup() {

    callbackService.callbackGormService = callbackGormService
    callbackService.userService = userService

  }

  def 'method worked successfully'() {

    given:
      def id = 1L
    when:
      1 * callbackGormService.get( _ as Long ) >>
          new Callback( user: new User() )
      1 * userService.getCurrent() >> new User()
      def result = callbackService.findOne( id )
    then:
      result instanceof CallbackDto

  }

  def 'instance not found (null)'() {

    given:
      def id = 1L
    when:
      1 * callbackGormService.get( _ as Long ) >> null
      callbackService.findOne( id )
    then:
      ItemNotFoundException e = thrown()
      e.message == 'callback.not.found'

  }

  def 'instance not found (already deleted)'() {

    given:
      def id = 1L
    when:
      1 * callbackGormService.get( _ as Long ) >>
          new Callback( dateDeleted: new Date() )
      callbackService.findOne( id )
    then:
      ItemNotFoundException e = thrown()
      e.message == 'callback.not.found'

  }

  def 'instance from different user'() {

    given:
      def id = 1L
    when:
      1 * userService.getCurrent() >> new User( id: 1L )
      1 * callbackGormService.get( _ as Long ) >>
          new Callback( user: new User( id: 2L ) )
      callbackService.findOne( id )
    then:
      ItemNotFoundException e = thrown()
      e.message == 'callback.not.found'

  }

  def "parameter 'id' is null"() {

    given:
      def id = null
    when:
      callbackService.findOne( id )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'callbackService.findOne.id.null'

  }

}
