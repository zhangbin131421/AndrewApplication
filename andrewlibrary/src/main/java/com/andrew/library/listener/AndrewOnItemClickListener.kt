package com.andrew.library.listener

interface AndrewOnItemClickListener<T> {
    fun onClick(list: List<T>?, position: Int)
}