package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.services.UserApiDataService

import io.micronaut.spring.tx.annotation.Transactional

import javax.inject.Singleton

@Singleton 
class UserApiDataServiceImpl implements UserApiDataService {

  @Override
  @Transactional(readOnly = true)
  UserApiData findByUser( User user ) throws Exception {

    if ( user == null ) {
      throw new IllegalArgumentException(
          'userApiDataService.findByUser.user.null' )
    }

    return UserApiData.findByUser( user )
  }

}
