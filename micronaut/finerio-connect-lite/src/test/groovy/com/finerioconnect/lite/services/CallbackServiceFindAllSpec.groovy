package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.dtos.ApiListDto
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.exceptions.BadRequestException
import com.finerioconnect.lite.services.impl.CallbackServiceImpl

import spock.lang.Specification

class CallbackServiceFindAllSpec extends Specification {

  def callbackService = new CallbackServiceImpl()

  def callbackGormService = Mock( CallbackGormService )
  def userService = Mock( UserService )

  def setup() {

    callbackService.callbackGormService = callbackGormService
    callbackService.userService = userService

  }

  def 'method worked successfully'() {

    when:
      1 * userService.getCurrent() >> new User()
      1 * callbackGormService.findByUser( _ as User ) >>
          [ new Callback(), new Callback() ]
      def result = callbackService.findAll()
    then:
      result instanceof ApiListDto
      result.data != null
      result.data[ 0 ] instanceof CallbackDto

  }

}
