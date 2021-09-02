package com.example.gitbrowser.home.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gitbrowser.db.GitBrowserDatabase;
import com.example.gitbrowser.home.interfaces.HomeApiInterface;
import com.example.gitbrowser.home.models.Contributors;
import com.example.gitbrowser.home.models.GitRepo;
import com.example.gitbrowser.home.models.RepoSearchResponse;
import com.example.gitbrowser.utils.CommonUtils;
import com.example.gitbrowser.utils.Constants;

import java.util.List;
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


    public void getRepo(MutableLiveData<RepoSearchResponse> repoListLiveData,
                                          MutableLiveData<Throwable> throwableMutableLiveData)
    {
        if (CommonUtils.INSTANCE.checkConnection())
        {
            mRestApi.getRepoList("popular", Constants.PER_PAGE)
                    .enqueue(new Callback<RepoSearchResponse>() {
                        @Override
                        public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {

                            if (response.isSuccessful())
                            {
                               // saveRepoLocally(response.body());
                                repoListLiveData.setValue(response.body());
                            }
                            else
                            {
                                Throwable throwable = new Throwable("Server Failure");
                                throwableMutableLiveData.setValue(throwable);
                            }
                        }

                        @Override
                        public void onFailure(Call<RepoSearchResponse> call, Throwable t) {
                            throwableMutableLiveData.setValue(t);
                        }
                    });
        }
        else
        {
            executor.execute(() ->{
                List<GitRepo> gitRepos = db.gitRepoDao().getRepoList();

                if (gitRepos.size() >0)
                {
                    RepoSearchResponse repoSearchResponse = new RepoSearchResponse(gitRepos.size(), false, gitRepos);
                    repoListLiveData.postValue(repoSearchResponse);
                }
                else
                {
                    Throwable throwable = new Throwable("No Internet Connection");
                    throwableMutableLiveData.postValue(throwable);
                }

            });


        }

    }


    public void getContributors(MutableLiveData<List<Contributors>> contributorLiveData,
                        MutableLiveData<Throwable> throwableMutableLiveData,
                                String url)
    {
        if (CommonUtils.INSTANCE.checkConnection())
        {
            mRestApi.getContributorsList(url)
                    .enqueue(new Callback<List<Contributors>>() {
                        @Override
                        public void onResponse(Call<List<Contributors>> call, Response<List<Contributors>> response) {

                            if (response.isSuccessful())
                            {
                                contributorLiveData.setValue(response.body());
                            }
                            else
                            {
                                Throwable throwable = new Throwable("Server Failure");
                                throwableMutableLiveData.setValue(throwable);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Contributors>> call, Throwable t) {
                            throwableMutableLiveData.setValue(t);
                        }
                    });
        }
        else
        {

            Throwable throwable = new Throwable("No Internet Connection");
            throwableMutableLiveData.setValue(throwable);
        }

    }

    public void getRepoWithQuery(MutableLiveData<RepoSearchResponse> repoListLiveData,
                            MutableLiveData<Throwable> throwableMutableLiveData,
                            String q)
    {
        if (CommonUtils.INSTANCE.checkConnection())
        {
            mRestApi.getRepoList(q, Constants.PER_PAGE)
                    .enqueue(new Callback<RepoSearchResponse>() {
                        @Override
                        public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {

                            if (response.isSuccessful())
                                repoListLiveData.setValue(response.body());
                            else
                            {
                                Throwable throwable = new Throwable("Server Failure");
                                throwableMutableLiveData.setValue(throwable);
                            }
                        }

                        @Override
                        public void onFailure(Call<RepoSearchResponse> call, Throwable t) {

                            throwableMutableLiveData.setValue(t);
                        }
                    });
        }
        else
        {
            Throwable throwable = new Throwable("No Internet Connection");
            throwableMutableLiveData.setValue(throwable);
        }

    }


    public void getRepoWithQueryPage(MutableLiveData<RepoSearchResponse> repoListLiveData,
                                 MutableLiveData<Throwable> throwableMutableLiveData,
                                 String q, int page)
    {
        if (CommonUtils.INSTANCE.checkConnection())
        {
            mRestApi.getRepoListPage(q, Constants.PER_PAGE, page)
                    .enqueue(new Callback<RepoSearchResponse>() {
                        @Override
                        public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {

                            if (response.isSuccessful())
                                repoListLiveData.setValue(response.body());
                            else
                            {
                                Throwable throwable = new Throwable("Server Failure");
                                throwableMutableLiveData.setValue(throwable);
                            }
                        }

                        @Override
                        public void onFailure(Call<RepoSearchResponse> call, Throwable t) {

                            throwableMutableLiveData.setValue(t);
                        }
                    });
        }
        else
        {
            Throwable throwable = new Throwable("No Internet Connection");
            throwableMutableLiveData.setValue(throwable);
        }

    }

    public void saveRepoLocally(RepoSearchResponse repoSearchResponse)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                for (GitRepo gitRepo: repoSearchResponse.getItems())
                    db.gitRepoDao().insert(gitRepo);
            }
        });
    }

}