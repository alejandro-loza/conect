package com.finerioconnect.lite.security

import com.finerioconnect.lite.services.UserGormService
import groovy.transform.CompileStatic
import io.micronaut.security.authentication.providers.UserFetcher
import io.micronaut.security.authentication.providers.UserState
import io.reactivex.Flowable
import org.reactivestreams.Publisher

import javax.inject.Singleton

@CompileStatic
@Singleton 
class UserFetcherService implements UserFetcher {

  protected final UserGormService userGormService

  UserFetcherService(UserGormService userGormService) { 
    this.userGormService = userGormService
  }

  @Override
  Publisher<UserState> findByUsername(String username) {

    UserState user = userGormService.findByUsername(username) as UserState
    (user ? Flowable.just(user) : Flowable.empty()) as Publisher<UserState>

  }

}
