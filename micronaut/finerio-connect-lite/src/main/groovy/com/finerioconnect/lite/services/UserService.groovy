package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.User

interface UserService {

  User getCurrent() throws Exception

}

