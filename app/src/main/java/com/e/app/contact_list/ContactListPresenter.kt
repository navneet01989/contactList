package com.e.app.contact_list

class ContactListPresenter(val view: View) {
    fun fetchContacts() {
        view.fetchContacts()
    }
    interface View {
        fun fetchContacts()
    }
}