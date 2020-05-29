package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.exceptions.ItemNotFoundException
import com.finerioconnect.lite.services.UserGormService
import com.finerioconnect.lite.services.UserService

import io.micronaut.security.utils.SecurityService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl implements UserService {

  @Inject
  SecurityService securityService

  @Inject
  UserGormService userGormService

  @Override
  User getCurrent() throws Exception {

    def username = securityService.username().get()
    return userGormService.findByUsername( username )

  }

  @Override
  User findOne( Long userId ) throws Exception {

    if ( userId == null ) {
      throw new IllegalArgumentException(
          'userService.findOne.userId.null' )
    }

    def user = userGormService.findById( userId )

    if ( user == null ) {
      throw new ItemNotFoundException( 'user.not.found' )
    }

    return user

  }

}

