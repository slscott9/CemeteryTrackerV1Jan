package com.sscott.cemeterytrackerv1.other

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sscott.cemeterytrackerv1.data.models.domain.CemeteryDomain
import com.sscott.cemeterytrackerv1.data.models.domain.GraveDomain

@BindingAdapter("setCemGraveCount")
fun TextView.setCemGraveCount(item: CemeteryDomain?){
    item?.name?.let {
        text = item.graveCount.toString()

    }
}

@BindingAdapter("setGraveName")
fun TextView.setGraveName(item: GraveDomain?){
    item?.let {
        text = "${item.firstName} ${item.lastName}"

    }
}