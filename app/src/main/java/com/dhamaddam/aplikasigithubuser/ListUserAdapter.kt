package com.dhamaddam.aplikasigithubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhamaddam.aplikasigithubuser.databinding.ItemRowUserBinding

class ListUserAdapter (private val listUser: ArrayList<UserGithub>): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (Username, Name, Avatar) = listUser[position]
        holder.binding.imgItemPhoto.setImageResource(Avatar)
        holder.binding.tvItemName.text = Username
        holder.binding.tvItemDescription.text = Name
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserGithub)
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding:ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}