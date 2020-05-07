package com.finerioconnect.lite.dtos

import groovy.transform.ToString

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ToString(includeNames = true, includePackage = false)
class CreateCallbackDto {

  @NotBlank
  @Size(min = 1, max = 200)
  String url

  @NotBlank
  @Size(min = 1, max = 20)
  String nature

}
