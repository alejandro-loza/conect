package com.finerioconnect.lite.dtos

import com.fasterxml.jackson.annotation.JsonInclude

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@JsonInclude(JsonInclude.Include.ALWAYS)
class ApiListDto {

  List<Object> data
  Long nextCursor

}
