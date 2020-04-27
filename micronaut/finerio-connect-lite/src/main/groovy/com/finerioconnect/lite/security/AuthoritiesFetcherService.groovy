package com.finerioconnect.lite.security

import com.finerioconnect.lite.services.UserRoleGormService
import io.micronaut.security.authentication.providers.AuthoritiesFetcher
import io.reactivex.Flowable
import org.reactivestreams.Publisher

import javax.inject.Singleton

@Singleton 
class AuthoritiesFetcherService implements AuthoritiesFetcher {

  protected final UserRoleGormService userRoleGormService

  AuthoritiesFetcherService(UserRoleGormService userRoleGormService) {   
    this.userRoleGormService = userRoleGormService
  }

  @Override
  Publisher<List<String>> findAuthoritiesByUsername(String username) {
    Flowable.just(userRoleGormService.findAllAuthoritiesByUsername(username))
  }

}
