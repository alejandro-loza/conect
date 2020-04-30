package com.finerioconnect.lite.dtos

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
class Error {

  String code
  String title
  String detail

}
