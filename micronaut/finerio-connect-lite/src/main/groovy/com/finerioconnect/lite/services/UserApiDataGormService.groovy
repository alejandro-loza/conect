package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.domain.UserApiData
import grails.gorm.services.Service

@Service(UserApiData)
interface UserApiDataGormService {

  UserApiData findByUser( User user )

}
