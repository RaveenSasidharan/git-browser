package com.example.gitbrowser.home.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitbrowser.databinding.ListContributorItemBinding
import com.example.gitbrowser.home.models.Contributors

class ContributorAdapter(private val contributorList: List<Contributors>): RecyclerView.Adapter<ContributorAdapter.ContributorHolder> (){



    inner class ContributorHolder(val binding: ListContributorItemBinding)
        :RecyclerView.ViewHolder(binding.root) {

            fun bind(contributors: Contributors)
            {
                Glide
                    .with(binding.root.context)
                    .load(contributors.avatar_url)
                    .into(binding.contriImage);
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListContributorItemBinding.inflate(layoutInflater, parent, false)

        return ContributorHolder(binding)
    }

    override fun onBindViewHolder(holder: ContributorHolder, position: Int) {
        holder.bind(contributorList[position])
    }

    override fun getItemCount(): Int {
        return contributorList.size
    }
}