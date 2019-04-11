package com.e.app.models

data class User(val id: Int, val first_name: String, val last_name: String, val avatar: String)

data class RetroCrypto(val page: Int, val per_page: Int, val total: Int, val total_pages: Int, val data: List<User>)
