package com.e.app.models

object Model {

    data class User(val id: Int, val first_name: String, val last_name: String, val avatar: String)

    data class ContactResponse(val page: Int, val per_page: Int, val total: Int, val total_pages: Int, val data: MutableList<User>)
}

