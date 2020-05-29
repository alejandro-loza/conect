package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Role
import grails.gorm.services.Service

@Service(Role) 
interface RoleGormService {

  Role save( String authority )
  Role find( String authority )

}
