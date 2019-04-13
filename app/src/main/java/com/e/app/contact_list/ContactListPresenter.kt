package com.e.app.contact_list

import com.e.app.models.Model

class ContactListPresenter(val view: View) {
    fun fetchContacts() {
        view.fetchContacts()
    }
    fun handleResponse(contactResponse: Model.ContactResponse?) {
        view.handleResponse(contactResponse)
    }
    fun handleError(error: Throwable) {
        view.handleError(error)
    }
    interface View {
        fun fetchContacts()
        fun handleResponse(contactResponse: Model.ContactResponse?)
        fun handleError(error: Throwable)
    }
}