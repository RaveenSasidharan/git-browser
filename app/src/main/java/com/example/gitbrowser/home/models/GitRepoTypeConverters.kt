package com.example.gitbrowser.home.models

import androidx.room.TypeConverter
import com.google.gson.Gson

class GitRepoTypeConverters {

    @TypeConverter
    fun fromOwnerObject(owner: Owner) : String{
        val gson = Gson()
        return gson.toJson(owner)
    }


    @TypeConverter
    fun toOwnerObject(owner : String) : Owner{
        val gson = Gson()
        return gson.fromJson(owner, Owner::class.java)
    }


    @TypeConverter
    fun fromLicenseObject(license: License) : String{
        val gson = Gson()
        return gson.toJson(license)
    }


    @TypeConverter
    fun toLicenseObject(license : String) : License{
        val gson = Gson()
        return gson.fromJson(license, License::class.java)
    }
}