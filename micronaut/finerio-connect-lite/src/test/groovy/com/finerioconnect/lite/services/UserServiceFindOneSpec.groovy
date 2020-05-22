package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.exceptions.ItemNotFoundException
import com.finerioconnect.lite.services.impl.UserServiceImpl

import spock.lang.Specification

class UserServiceFindOneSpec extends Specification {

  def userService = new UserServiceImpl()

  def userGormService = Mock( UserGormService )

  def setup() {
    userService.userGormService = userGormService
  }

  def 'method worked successfully'() {

    given:
      def id = 1L
    when:
      1 * userGormService.findById( _ as Long ) >> new User()
      def result = userService.findOne( id )
    then:
      result instanceof User

  }

  def 'instance not found'() {

    given:
      def id = 1L
    when:
      1 * userGormService.findById( _ as Long ) >> null
      def result = userService.findOne( id )
    then:
      ItemNotFoundException e = thrown()
      e.message == 'user.not.found'

  }

  def "parameter 'id' is null"() {

    given:
      def id = null
    when:
      userService.findOne( id )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'userService.findOne.userId.null'

  }

}
