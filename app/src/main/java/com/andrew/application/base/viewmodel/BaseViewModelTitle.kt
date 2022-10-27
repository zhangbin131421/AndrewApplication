package com.example.kotlin.base.viewmodel

import com.andrew.application.base.viewmodel.BaseViewModel
import com.andrew.application.mode.LayoutTitle


open class BaseViewModelTitle : BaseViewModel() {
    init {
        layoutTitle = LayoutTitle()
    }
}