package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User
import grails.gorm.services.Service

@Service(User) 
interface UserGormService {

  User save( String username, String password )

  User findByUsername( String username )

  User findById(Serializable id) 

}
