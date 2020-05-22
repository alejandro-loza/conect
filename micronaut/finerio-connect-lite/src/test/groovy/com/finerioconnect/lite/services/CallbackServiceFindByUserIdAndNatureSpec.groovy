package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.dtos.CallbackDto
import com.finerioconnect.lite.services.impl.CallbackServiceImpl

import spock.lang.Specification

class CallbackServiceFindByUserIdAndNatureSpec extends Specification {

  def callbackService = new CallbackServiceImpl()

  def callbackGormService = Mock( CallbackGormService )
  def userService = Mock( UserService )

  def setup() {

    callbackService.callbackGormService = callbackGormService
    callbackService.userService = userService

  }

  def 'method worked successfully'() {

    given:
      def userId = 1L
      def nature = Callback.Nature.NOTIFY
    when:
      def result = callbackService.findByUserIdAndNature(
          userId, nature )
    then:
      1 * userService.findOne( _ as Long ) >> new User()
      1 * callbackGormService.findByUserAndNatureAndDateDeletedIsNull(
          _ as User, _ as Callback.Nature ) >> new Callback()
      result instanceof CallbackDto

  }

  def 'user not found'() {

    given:
      def userId = 1L
      def nature = Callback.Nature.NOTIFY
    when:
      callbackService.findByUserIdAndNature( userId, nature )
    then:
      1 * userService.findOne( _ as Long ) >> null
      IllegalArgumentException e = thrown()
      e.message == 'callbackService.findByUserIdAndNature.user.not.found'

  }

  def 'callback not found'() {

    given:
      def userId = 1L
      def nature = Callback.Nature.NOTIFY
    when:
      def result = callbackService.findByUserIdAndNature(
          userId, nature )
    then:
      1 * userService.findOne( _ as Long ) >> new User()
      1 * callbackGormService.findByUserAndNatureAndDateDeletedIsNull(
          _ as User, _ as Callback.Nature ) >> null
      result == null

  }

  def "parameter 'userId' is null"() {

    given:
      def userId = null
      def nature = Callback.Nature.NOTIFY
    when:
      callbackService.findByUserIdAndNature( userId, nature )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'callbackService.findByUserIdAndNature.userId.null'

  }

  def "parameter 'nature' is null"() {

    given:
      def userId = 1L
      def nature = null
    when:
      callbackService.findByUserIdAndNature( userId, nature )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'callbackService.findByUserIdAndNature.nature.null'

  }

}

