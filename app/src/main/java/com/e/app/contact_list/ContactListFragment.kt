package com.e.app.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.e.app.R

class ContactListFragment: Fragment(), ContactListPresenter.View {
    private lateinit var contactListPresenter: ContactListPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.contact_list_fragment, container, false)
        val rcyContactList = rootView.findViewById<RecyclerView>(R.id.rcyContactList)
        contactListPresenter = ContactListPresenter(this)
        contactListPresenter.fetchContacts()
        return rootView
    }
    override fun fetchContacts() {

    }
}