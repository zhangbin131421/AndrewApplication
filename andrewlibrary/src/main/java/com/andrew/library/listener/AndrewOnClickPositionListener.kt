package com.andrew.library.listener

interface AndrewOnClickPositionListener<T> {
    fun onClick(t: T, position: Int)
}