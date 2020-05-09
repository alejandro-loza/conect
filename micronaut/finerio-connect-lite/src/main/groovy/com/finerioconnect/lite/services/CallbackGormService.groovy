package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.Callback.Nature
import com.finerioconnect.lite.domain.User
import grails.gorm.services.Service

@Service(Callback) 
interface CallbackGormService {

  Callback save( Callback callback )

  Callback findByUserAndNature( User user, Nature nature )

  List<Callback> findByUser( User user )

  Callback get( Serializable id )

}
