package com.example.gitbrowser.home.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.gitbrowser.home.models.GitRepo;

@Dao
public interface GitRepoDao {

    @Insert
    void insert(GitRepo gitRepo);
}
