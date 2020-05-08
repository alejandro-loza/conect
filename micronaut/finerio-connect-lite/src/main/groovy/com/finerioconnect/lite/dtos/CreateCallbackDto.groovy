package com.finerioconnect.lite.dtos

import groovy.transform.ToString

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ToString(includeNames = true, includePackage = false)
class CreateCallbackDto {

  @NotBlank( message = 'callback.url.blank')
  @Size(min = 1, max = 200, message = 'callback.url.size.invalid')
  String url

  @NotBlank( message = 'callback.nature.blank')
  @Size(min = 1, max = 20, message = 'callback.nature.size.invalid')
  String nature

}
