package com.finerioconnect.lite.services.impl

import com.finerioconnect.lite.services.MessageService

import io.micronaut.context.MessageSource

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageServiceImpl implements MessageService {

  @Inject
  MessageSource messageSource

  @Override
  String getMessage( String key ) throws Exception {

    def context = MessageSource.MessageContext.DEFAULT
    return messageSource.getMessage( key, context, key )

  }

}
