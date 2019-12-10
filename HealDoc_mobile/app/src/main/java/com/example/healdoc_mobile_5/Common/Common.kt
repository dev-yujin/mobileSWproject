package com.example.healdoc_mobile_5.Common

import com.example.healdoc_mobile_5.Remote.IGoogleAPIService
import com.example.healdoc_mobile_5.Remote.RetrofitClient

object Common {

    private val GOOGLE_API_URL="https://maps.googleapis.com/"

    val googleApiService:IGoogleAPIService
        get() = RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}