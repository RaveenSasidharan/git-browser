package com.example.gitbrowser;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.example.gitbrowser.di.component.ApplicationComponent;
import com.example.gitbrowser.di.component.DaggerApplicationComponent;
import com.example.gitbrowser.di.module.ApplicationModule;
import com.example.gitbrowser.utils.CommonUtils;

public class GitBrowserApplication extends MultiDexApplication {

    private static GitBrowserApplication applicationContext;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ApplicationComponent mApplicationComponent;

    public static GitBrowserApplication app() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CommonUtils.context = this;

        applicationContext = this;
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}

