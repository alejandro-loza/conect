package com.finerioconnect.lite.domain

import grails.gorm.annotation.Entity

import groovy.transform.ToString

import org.grails.datastore.gorm.GormEntity

@Entity 
@ToString(includeNames = true, includePackage = false)
class CredentialConnectionHistory
    implements GormEntity<CredentialConnectionHistory> {

  CredentialConnection credentialConnection
  String stage
  Date dateCreated
  Date lastUpdated
  Date dateDeleted

  static constraints = { 
    stage nullable: false, blank: false, size: 1..50
    dateDeleted nullable: true
  }

}
