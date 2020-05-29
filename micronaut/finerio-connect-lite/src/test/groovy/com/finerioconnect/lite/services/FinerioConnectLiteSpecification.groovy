package com.finerioconnect.lite.services

import spock.lang.Specification

class FinerioConnectLiteSpecification extends Specification {

  Properties getProps() {

    def input = new FileInputStream( 'testing.properties' )
    def props = new Properties()
    props.load( input )
    return props

  }

}
