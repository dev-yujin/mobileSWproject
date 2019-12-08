package com.example.healdoc_mobile_5

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class bookInfo (
    var hour: String? = "",
    var tea: String? = "",
    var sub: String? ="",
    var con : String? =""
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "con" to con,
            "tea" to tea,
            "sub" to sub,
            "hour" to hour
        )
    }
}