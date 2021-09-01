package com.example.gitbrowser.home.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gitbrowser.db.GitBrowserDatabase;
import com.example.gitbrowser.home.interfaces.HomeApiInterface;
import com.example.gitbrowser.home.models.GitRepo;
import com.example.gitbrowser.home.models.RepoSearchResponse;
import com.example.gitbrowser.utils.CommonUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private static HomeRepository instance;

    private HomeApiInterface mRestApi;
    private Context context;
    private GitBrowserDatabase db;

    Executor executor = Executors.newSingleThreadExecutor();

    public static HomeRepository getInstance() {
        if (instance == null) {
            synchronized(HomeRepository.class) {
                if (instance == null) {
                    instance = new HomeRepository();
                }
            }
        }
        return instance;
    }

    public void setUp(HomeApiInterface homeApiInterface, Context context, GitBrowserDatabase db)
    {
        this.mRestApi = homeApiInterface;
        this.context          = context;
        this.db = db;
    }


    public void getRepo(MutableLiveData<RepoSearchResponse> movieListResponseMutableLiveData,
                                          MutableLiveData<Throwable> throwableMutableLiveData)
    {
        if (CommonUtils.INSTANCE.checkConnection())
        {
            mRestApi.getRepoList("popular")
                    .enqueue(new Callback<RepoSearchResponse>() {
                        @Override
                        public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {

                            if (response.isSuccessful())
                                movieListResponseMutableLiveData.setValue(response.body());
                            else
                            {


                                Throwable throwable = new Throwable("Server Failure");
                                throwableMutableLiveData.setValue(throwable);
                            }
                        }

                        @Override
                        public void onFailure(Call<RepoSearchResponse> call, Throwable t) {
                            Log.e("movie","onFailure ->"+t.getMessage());

                            throwableMutableLiveData.setValue(t);
                        }
                    });
        }
        else
        {
            Log.e("movie","getNewsFeed no internet");

            Throwable throwable = new Throwable("No Internet Connection");
            throwableMutableLiveData.setValue(throwable);
        }

    }


    public void getRepoWithQuery(MutableLiveData<RepoSearchResponse> movieListResponseMutableLiveData,
                            MutableLiveData<Throwable> throwableMutableLiveData,
                            String q)
    {
        if (CommonUtils.INSTANCE.checkConnection())
        {
            mRestApi.getRepoList(q)
                    .enqueue(new Callback<RepoSearchResponse>() {
                        @Override
                        public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {

                            if (response.isSuccessful())
                                movieListResponseMutableLiveData.setValue(response.body());
                            else
                            {
                                Throwable throwable = new Throwable("Server Failure");
                                throwableMutableLiveData.setValue(throwable);
                            }
                        }

                        @Override
                        public void onFailure(Call<RepoSearchResponse> call, Throwable t) {
                            Log.e("movie","onFailure ->"+t.getMessage());

                            throwableMutableLiveData.setValue(t);
                        }
                    });
        }
        else
        {
            Log.e("movie","getNewsFeed no internet");

            Throwable throwable = new Throwable("No Internet Connection");
            throwableMutableLiveData.setValue(throwable);
        }

    }



    public void saveRepoLocally(GitRepo gitRepo)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.gitRepoDao().insert(gitRepo);
            }
        });
    }

}