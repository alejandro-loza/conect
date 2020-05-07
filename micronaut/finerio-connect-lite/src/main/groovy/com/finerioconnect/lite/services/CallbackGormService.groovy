package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import grails.gorm.services.Service

@Service(Callback) 
interface CallbackGormService {

  Callback save( Callback callback )

}
