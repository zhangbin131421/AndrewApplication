package com.andrew.application.mode.response.wan

class ListProjectBean {
    val curPage: String?=null;
    val pageCount: Int?=null;
    val total: Int?=null;
    override fun toString(): String {
        return "ListProjectBean(curPage=$curPage, pageCount=$pageCount, total=$total)"
    }
}