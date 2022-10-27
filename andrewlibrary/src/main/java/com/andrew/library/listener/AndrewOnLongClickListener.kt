package com.andrew.library.listener

interface AndrewOnLongClickListener<T> {
    fun onLongClick(t: T, position: Int)
}