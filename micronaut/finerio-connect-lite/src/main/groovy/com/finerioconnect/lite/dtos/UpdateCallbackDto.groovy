package com.finerioconnect.lite.dtos

import groovy.transform.ToString

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ToString(includeNames = true, includePackage = false)
class UpdateCallbackDto {

  @NotBlank(message = 'callback.url.blank')
  @Size(min = 1, max = 200, message = 'callback.url.size.invalid')
  String url

}
