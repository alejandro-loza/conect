package com.finerioconnect.lite.services

import com.finerioconnect.lite.Application
import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.domain.UserApiData

import io.micronaut.test.annotation.MicronautTest

import javax.inject.Inject

import spock.lang.Specification

@MicronautTest(application = Application.class)
class UserApiDataServiceFindByUserSpec extends Specification {

  @Inject
  UserApiDataService userApiDataService

  def 'method worked successfully'() {

    given:
      def user = createUser( 'username' )
      def userApiData = createUserApiData( user )
    when:
      def instanceToFind = userApiDataService.findByUser( user )
    then:
      instanceToFind != null
    cleanup:
      deleteUserApiData( userApiData )
      deleteUser( user )

  }

  def 'entity not found'() {

    given:
      def user = createUser( 'username' )
    when:
      def instanceToFind = userApiDataService.findByUser( user )
    then:
      instanceToFind == null
    cleanup:
      deleteUser( user )

  }

  def "parameter 'user' is null"() {

    given:
      def user = null
    when:
      def instanceToFind = userApiDataService.findByUser( user )
    then:
      IllegalArgumentException e = thrown()
      e.message == 'userApiDataService.findByUser.user.null'

  }

  private User createUser( String username ) throws Exception {

    def user = new User(
      username: username,
      password: 'password'
    )
    User.withTransaction { status ->
      user.save( failOnError: true )
    }

    return user

  }

  private UserApiData createUserApiData( User user ) throws Exception {

    def userApiData = new UserApiData(
      user: user,
      username: 'username',
      password: 'password',
      clientId: 'clientId',
      clientSecret: 'clientSecret'
    )
    UserApiData.withTransaction { status ->
      userApiData.save( failOnError: true )
    }

    return userApiData

  }

  private void deleteUser( User user ) throws Exception {

    User.withTransaction { status ->
      user.delete()
    }

  }

  private void deleteUserApiData( UserApiData userApiData )
      throws Exception {

    UserApiData.withTransaction { status ->
      userApiData.delete()
    }

  }

}
