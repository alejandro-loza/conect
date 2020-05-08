package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.dtos.CreateCallbackDto
import com.finerioconnect.lite.exceptions.BadRequestException
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
      1 * callbackGormService.findByUserAndNature(
          _ as User, _ as Callback.Nature ) >> null
      1 * callbackGormService.save( _ as Callback ) >> new Callback()
      def result = callbackService.create( createCallbackDto )
    then:
      result instanceof CallbackDto

  }

  def 'callback previously created'() {

    given:
      def createCallbackDto = new CreateCallbackDto()
      createCallbackDto.nature = 'TRANSACTIONS'
    when:
      1 * userService.getCurrent() >> new User()
      1 * callbackGormService.findByUserAndNature(
          _ as User, _ as Callback.Nature ) >> new Callback()
      callbackService.create( createCallbackDto )
    then:
      BadRequestException e = thrown()
      e.message == 'callback.exists'

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

  def "parameter 'createCallbackDto.nature' is null"() {

    given:
      def createCallbackDto = new CreateCallbackDto()
      createCallbackDto.nature = null
    when:
      1 * userService.getCurrent() >> new User()
      callbackService.create( createCallbackDto )
    then:
      BadRequestException e = thrown()
      e.message == 'callback.nature.invalid'

  }

  def "parameter 'createCallbackDto.nature' is invalid"() {

    given:
      def createCallbackDto = new CreateCallbackDto()
      createCallbackDto.nature = 'INVALID_NATURE'
    when:
      1 * userService.getCurrent() >> new User()
      callbackService.create( createCallbackDto )
    then:
      BadRequestException e = thrown()
      e.message == 'callback.nature.invalid'

  }

}
