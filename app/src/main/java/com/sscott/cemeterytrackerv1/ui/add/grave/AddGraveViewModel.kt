package com.sscott.cemeterytrackerv1.ui.add.grave

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.sscott.cemeterytrackerv1.data.repository.Repository

class AddGraveViewModel @ViewModelInject constructor(
        private val repository: Repository
): ViewModel() {


}