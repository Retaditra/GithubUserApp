package com.kotlin.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.core.databinding.ListUserBinding
import com.kotlin.core.domain.model.User

class UserAdapter(var onItemClick: (User) -> Unit) :
    ListAdapter<User, UserAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private val binding: ListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.avatarUrl)
                    .into(ivAvatar)
                tvName.text = data.username
            }
            binding.root.setOnClickListener { onItemClick(data) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}
