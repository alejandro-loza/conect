package com.finerioconnect.lite.services

import com.finerioconnect.lite.Application
import com.finerioconnect.lite.domain.UserApiData
import com.finerioconnect.lite.dtos.CreateCredentialDto

import io.micronaut.test.annotation.MicronautTest

import javax.inject.Inject

import spock.lang.Specification

@MicronautTest(application = Application.class)
class FinerioConnectApiServiceCreateCredentialSpec
    extends FinerioConnectLiteSpecification {

  @Inject
  FinerioConnectApiService finerioConnectApiService

  def 'method worked successfully'() {

    given:
      def props = getProps()
      def userApiData = new UserApiData(
        username: props.'finerio-connect.username',
        password: props.'finerio-connect.password',
        clientId: props.'finerio-connect.clientId',
        clientSecret: props.'finerio-connect.clientSecret'
      )
      def createCredentialDto = new CreateCredentialDto(
        customerId: props.'finerio-connect.customerId' as Long,
        bankId: props.'finerio-connect.bankId' as Long,
        username: props.'finerio-connect.credentialUsername',
        password: props.'finerio-connect.credentialPassword'
      )
    when:
      def credentialDto = finerioConnectApiService.createCredential(
          userApiData, createCredentialDto )
    then:
      credentialDto != null

  }

}
