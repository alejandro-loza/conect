package com.finerioconnect.lite.domain

import grails.gorm.annotation.Entity

import groovy.transform.ToString

import org.grails.datastore.gorm.GormEntity

@Entity 
@ToString(includeNames = true, includePackage = false)
class CredentialConnection implements GormEntity<CredentialConnection> {

  User user
  String credentialId
  Date dateCreated
  Date lastUpdated
  Date dateDeleted

  static constraints = { 
    dateDeleted nullable: true
  }

}
