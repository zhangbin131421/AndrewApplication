package com.andrew.application.base.viewmodel

import com.andrew.library.base.AndrewViewModel
import com.andrew.application.mode.LayoutTitle
import com.andrew.application.net.API

open class BaseViewModel : AndrewViewModel() {
    var layoutTitle: LayoutTitle? = null
    protected val api: API = API.Builder.getDefaultService()

}