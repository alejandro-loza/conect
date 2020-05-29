package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.services.impl.UserServiceImpl

import io.micronaut.security.utils.SecurityService

import spock.lang.Specification

class UserServiceCreateSpec extends Specification {

  def userService = new UserServiceImpl()

  def securityService = Mock( SecurityService )
  def userGormService = Mock( UserGormService )

  def setup() {

    userService.securityService = securityService
    userService.userGormService = userGormService

  }

  def 'method worked successfully'() {

    when:
      1 * securityService.username() >> Optional.of( 'username' )
      1 * userGormService.findByUsername( _ as String ) >> new User()
      def result = userService.getCurrent()
    then:
      result instanceof User

  }

}
