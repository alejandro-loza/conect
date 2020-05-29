package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User
import com.finerioconnect.lite.logging.Log

interface UserService {

  @Log
  User getCurrent() throws Exception

  @Log
  User findOne( Long userId ) throws Exception

}

