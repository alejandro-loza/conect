package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.domain.Role
import com.finerioconnect.lite.domain.UserRole
import grails.gorm.services.Query
import grails.gorm.services.Service

@Service(UserRole) 
interface UserRoleGormService {

  UserRole save( User user, Role role )

  UserRole find( User user, Role role )

  @Query("""select $r.authority
  from ${UserRole ur}
  inner join ${User u = ur.user}
  inner join ${Role r = ur.role}
  where $u.username = $username""") 
  List<String> findAllAuthoritiesByUsername( String username )

}
