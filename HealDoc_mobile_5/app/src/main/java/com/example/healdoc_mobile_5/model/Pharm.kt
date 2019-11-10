package com.example.healdoc_mobile_5.model

data class Pharm(
    val amount: String = "",
    val pharm_name: String = "",
    val purpose: String = "",
    val time: String = "",   // FIXME: Int
    val type: String = "",   // FIXME: ArrayList<String>
    val day: String = ""     // FIXME: Int
)