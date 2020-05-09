package com.finerioconnect.lite.services

import com.finerioconnect.lite.domain.Callback
import com.finerioconnect.lite.domain.Callback.Nature
import com.finerioconnect.lite.domain.User
import grails.gorm.services.Service

@Service(Callback) 
interface CallbackGormService {

  Callback save( Callback callback )

  Callback findByUserAndNatureAndDateDeletedIsNull(
      User user, Nature nature )

  List<Callback> findByUserAndDateDeletedIsNull( User user, Map args )

  Callback get( Serializable id )

}
