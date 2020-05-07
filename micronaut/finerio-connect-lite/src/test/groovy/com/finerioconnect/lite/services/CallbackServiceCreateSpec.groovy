package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CreateCallbackDto
import com.finerioconnect.lite.services.impl.CallbackServiceImpl

import spock.lang.Specification

class CallbackServiceCreateSpec extends Specification {

  def callbackService = new CallbackServiceImpl()

  def callbackGormService = Mock( CallbackGormService )
  def userService = Mock( UserService )

  def setup() {

    callbackService.callbackGormService = callbackGormService
    callbackService.userService = userService

  }

  def 'method worked successfully'() {

    given:
      def createCallbackDto = new CreateCallbackDto()
      createCallbackDto.nature = 'TRANSACTIONS'
    when:
      1 * userService.getCurrent() >> new User()
      1 * callbackGormService.save( _ as Callback ) >> new Callback()
      def result = callbackService.create( createCallbackDto )
    then:
      result instanceof CallbackDto

  }

  def "parameter 'createCallbackDto' is null"() {

    given:
      def createCallbackDto = null
    when:
      callbackService.create( createCallbackDto )
    then:
      IllegalArgumentException e = thrown()
      e.message ==
          'callbackService.create.createCallbackDto.null'

  }

}
