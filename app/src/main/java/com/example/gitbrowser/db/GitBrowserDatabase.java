package com.example.gitbrowser.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.gitbrowser.home.interfaces.GitRepoDao;
import com.example.gitbrowser.home.models.GitRepo;
import com.example.gitbrowser.home.models.GitRepoTypeConverters;
import com.example.gitbrowser.utils.Constants;


@Database(entities = {GitRepo.class},
        version = Constants.Db.CURRENT_DB_VERSION, exportSchema = false)

@TypeConverters({GitRepoTypeConverters.class})
public abstract class GitBrowserDatabase extends RoomDatabase {


    private static GitBrowserDatabase INSTANCE;

    public static GitBrowserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GitBrowserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GitBrowserDatabase.class, Constants.Db.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract GitRepoDao gitRepoDao();
}
