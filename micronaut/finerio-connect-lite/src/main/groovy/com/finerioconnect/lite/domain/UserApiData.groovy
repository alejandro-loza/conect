package com.finerioconnect.lite.domain

import grails.gorm.annotation.Entity
import io.micronaut.security.authentication.providers.UserState
import org.grails.datastore.gorm.GormEntity

@Entity 
class UserApiData implements GormEntity<UserApiData> {

  User user
  String username
  String password
  String clientId
  String clientSecret
  Date dateCreated
  Date lastUpdated

  static constraints = { 
    username nullable: false, blank: false
    password nullable: false, blank: false
    clientId nullable: false, blank: false
    clientSecret nullable: false, blank: false
  }

}
