package com.andrew.application.mode

data class Page<T>(var page: Int, var pageSize: Int, var rows: List<T>, var totalPage: Int, var totalRows: Int) {
}