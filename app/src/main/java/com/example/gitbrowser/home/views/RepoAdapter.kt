package com.example.gitbrowser.home.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitbrowser.databinding.ListRepoItemBinding
import com.example.gitbrowser.home.models.GitRepo

class RepoAdapter (private var repoList: MutableList<GitRepo>,
                   private val activity: AppCompatActivity) :RecyclerView.Adapter<RepoAdapter.RepoHolder>() {

    ///
    //viewholder for holding GitRepo data
    ///
    inner class RepoHolder(val activity: AppCompatActivity,
                              val binding:ListRepoItemBinding) : RecyclerView.ViewHolder(binding.root){

        private lateinit var gitRepo: GitRepo
        init {
            binding.repoHolder = this

        }

        fun bind(gitRepo: GitRepo)
        {
            this.gitRepo = gitRepo
            binding.title.text = gitRepo.full_name
            binding.date.text   = gitRepo.updated_at
            binding.language.text = gitRepo.language
            binding.star.text   = gitRepo.stargazers_count.toString()
            binding.fork.text   = gitRepo.forks_count.toString()

            Glide
                .with(binding.root.context)
                .load(gitRepo.owner.avatar_url)
                .into(binding.thumbnail);

        }



        fun openRepo()
        {
                val intent : Intent = Intent(activity,RepoDetailActivity::class.java)
                val bundle : Bundle = Bundle()
                bundle.putSerializable("repo", gitRepo)
                intent.putExtras(bundle)
                activity.startActivity(intent)
        }

    }//end RepoHolder


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListRepoItemBinding.inflate(layoutInflater, parent, false)

        return RepoHolder(activity, binding)
    }

    override fun onBindViewHolder(holder: RepoHolder, position: Int) {
        holder.bind(repoList[position])
    }

    override fun getItemCount(): Int {
        return repoList.size
    }


    fun addRepos(repos: List<GitRepo>)
    {
        repoList.addAll(repos)
    }
}