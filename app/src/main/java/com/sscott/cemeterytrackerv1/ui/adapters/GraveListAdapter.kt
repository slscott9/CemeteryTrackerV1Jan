package com.sscott.cemeterytrackerv1.ui.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.sscott.cemeterytrackerv1.R
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain
import com.sscott.cemeterytrackerv1.databinding.GraveListItemBinding

class GraveListAdapter(val listener : GraveListListener) : ListAdapter<GraveDomain, GraveListAdapter.ViewHolder>(GraveListDiffUtil()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = GraveListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding : GraveListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(grave : GraveDomain){
            binding.grave = grave

            binding.btnMore.setOnClickListener {
                if(binding.clMoreInfo.visibility == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(binding.cvParent, AutoTransition())
                    binding.btnMore.text = binding.root.context.getString(R.string.more)
                    binding.clMoreInfo.visibility = View.GONE

                }else{
                    TransitionManager.beginDelayedTransition(binding.cvParent, AutoTransition())
                    binding.clMoreInfo.visibility = View.VISIBLE
                    binding.btnMore.text = binding.root.context.getString(R.string.less)
                }
            }
        }
    }

    class GraveListDiffUtil() : DiffUtil.ItemCallback<GraveDomain>() {
        override fun areItemsTheSame(oldItem: GraveDomain, newItem: GraveDomain): Boolean {
            return oldItem.cemeteryId == newItem.cemeteryId
        }

        override fun areContentsTheSame(oldItem: GraveDomain, newItem: GraveDomain): Boolean {
            return oldItem == newItem
        }
    }

    class GraveListListener(val listener : (grave : GraveDomain) -> Unit){
        fun onClick(grave: GraveDomain){
            listener(grave)
        }
    }



}