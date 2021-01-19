package com.sscott.cemeterytrackerv1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.databinding.CemListItemBinding

class AllCemsListAdapter(val listener: (cem: CemeteryDomain) -> Unit) : ListAdapter<CemeteryDomain, AllCemsListAdapter.ViewHolder>(DiffUtilCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CemListItemBinding.inflate(layoutInflater, parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class ViewHolder(val binding : CemListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(cem: CemeteryDomain){
            binding.cemetery = cem
        }
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<CemeteryDomain>(){
        override fun areItemsTheSame(oldItem: CemeteryDomain, newItem: CemeteryDomain): Boolean {
            return oldItem.cemeteryId == newItem.cemeteryId
        }

        override fun areContentsTheSame(oldItem: CemeteryDomain, newItem: CemeteryDomain): Boolean {
            return oldItem == newItem
        }
    }

    class AllCemsListener(val listener : (cem : CemeteryDomain) -> Unit){
        fun onClick(cem: CemeteryDomain){
            listener(cem)
        }
    }


}