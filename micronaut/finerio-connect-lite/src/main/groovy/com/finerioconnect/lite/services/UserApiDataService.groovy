package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.domain.UserApiData

interface UserApiDataService {

  UserApiData findByUser( User user ) throws Exception

}
