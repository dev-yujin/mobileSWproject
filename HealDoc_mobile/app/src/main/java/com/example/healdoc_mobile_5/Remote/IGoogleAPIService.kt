package com.example.healdoc_mobile_5.Remote

import com.example.healdoc_mobile_5.mModel.MyPlaces
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface IGoogleAPIService {
    @GET
    fun getNearbyPlaces(@Url url:String): Call<MyPlaces>
}