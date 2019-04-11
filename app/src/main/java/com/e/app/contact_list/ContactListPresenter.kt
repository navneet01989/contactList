package com.e.app.contact_list

import com.e.app.models.RetroCrypto

class ContactListPresenter(val view: View) {
    fun fetchContacts() {
        view.fetchContacts()
    }
    interface View {
        fun fetchContacts()
    }
}