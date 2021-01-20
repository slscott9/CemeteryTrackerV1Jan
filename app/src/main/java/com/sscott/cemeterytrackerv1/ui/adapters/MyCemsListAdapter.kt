package com.sscott.cemeterytrackerv1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.databinding.CemListItemBinding

class MyCemsListAdapter(listener: (cem: CemeteryDomain) -> Unit) : ListAdapter<CemeteryDomain, MyCemsListAdapter.ViewHolder>(DiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CemListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(val binding : CemListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(cemeteryDomain: CemeteryDomain){
            binding.cemetery = cemeteryDomain
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<CemeteryDomain>(){
        override fun areItemsTheSame(oldItem: CemeteryDomain, newItem: CemeteryDomain): Boolean {
            return oldItem.cemeteryId == newItem.cemeteryId
        }

        override fun areContentsTheSame(oldItem: CemeteryDomain, newItem: CemeteryDomain): Boolean {
            return oldItem == newItem
        }
    }

    class MyCemsListener(val listener : (cem : CemeteryDomain) -> Unit){

        fun onClick(cemeteryDomain: CemeteryDomain) {
            listener(cemeteryDomain)
        }
    }
}