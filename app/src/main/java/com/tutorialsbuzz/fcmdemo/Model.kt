package com.tutorialsbuzz.fcmdemo

import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("name")
    val name: String,

    @SerializedName("status")
    var status: Boolean
) {}