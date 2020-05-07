package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.logging.Log
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

  @Log
  User getCurrent() throws Exception {

    def username = securityService.username().get()
    return userGormService.findByUsername( username )

  }

}

