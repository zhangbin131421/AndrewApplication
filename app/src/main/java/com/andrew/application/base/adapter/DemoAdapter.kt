package com.andrew.application.base.adapter

import android.content.Context
import com.andrew.application.R
import com.andrew.application.mode.DemoData

class DemoAdapter(context: Context) : BaseRecyclerViewAdapter<DemoData>(context) {
    override val layoutId: Int = R.layout.item_demo


}