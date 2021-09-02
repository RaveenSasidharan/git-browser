package com.example.gitbrowser.home.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "git_repo_table")
data class GitRepo(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("full_name") val full_name : String?,
    @SerializedName("owner") val owner : Owner?,
    @SerializedName("html_url") val html_url : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("url") val url : String?,
    @SerializedName("created_at") val created_at : String?,
    @SerializedName("updated_at") val updated_at : String?,
    @SerializedName("stargazers_count") val stargazers_count : Int,
    @SerializedName("watchers_count") val watchers_count : Int,
    @SerializedName("language") val language : String?,
    @SerializedName("forks_count") val forks_count : Int,
    @SerializedName("contributors_url") val contributors_url : String?,
    @SerializedName("license") val license : License?
) : Serializable