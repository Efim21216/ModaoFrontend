package ru.nsu.fit.modao.utils

class Constants {
    companion object {
        //const val BASE_URL = "http://192.168.137.1:8080" // laptop wifi
        //const val BASE_URL = "http://192.168.137.52:8080"
        //const val BASE_URL = "http://192.168.137.3:8080" //wifi sister
        const val BASE_URL = "http://139.59.143.34:5000" //server
        //const val BASE_URL = "http://192.168.0.101:8080" //my wifi
        //const val BASE_URL = "http://192.168.0.107:8080"
        const val PAGE_SIZE = 10
        const val AUTH = "Bearer "
        const val ACCESS_TOKEN = "Access token"
        const val REFRESH_TOKEN = "Refresh token"
        const val ID_USER = "User id"
        const val BASE_URL_FCM = "https://fcm.googleapis.com"
    }
}