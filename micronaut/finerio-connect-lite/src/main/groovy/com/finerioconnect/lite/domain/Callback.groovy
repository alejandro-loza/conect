package com.finerioconnect.lite.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity 
class Callback implements GormEntity<Callback> {

   enum Nature {
    NOTIFY,
    SUCCESS,
    FAILURE,
    ACCOUNTS,
    TRANSACTIONS
  }

  User user
  String url
  Nature nature
  Date dateCreated
  Date lastUpdated
  Date dateDeleted

  static constraints = { 
    url nullable: false, blank: false, size: 1..200
    nature nullable: false, enumType: 'string'
    dateDeleted nullable: true
  }

}
