package com.example.gitbrowser.home.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitbrowser.home.models.GitRepo

@Dao
interface GitRepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gitRepo: GitRepo?)

    @Query("SELECT * FROM git_repo_table ")
    fun getRepoList(): List<GitRepo>
}