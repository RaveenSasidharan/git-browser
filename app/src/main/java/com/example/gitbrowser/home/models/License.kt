package com.example.gitbrowser.home.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.annotation.Nullable


data class License (
    @Nullable @SerializedName("name") val name : String,
    @Nullable @SerializedName("url") val url : String?
    ):Serializable