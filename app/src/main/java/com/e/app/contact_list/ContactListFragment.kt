package com.e.app.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.app.R
import com.e.app.adapters.ContactListAdapter
import com.e.app.api.GetData
import com.e.app.api.GetData.Companion.BASE_URL
import com.e.app.models.Model
import kotlinx.android.synthetic.main.contact_list_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContactListFragment: Fragment(), ContactListPresenter.View {

    private lateinit var contactListPresenter: ContactListPresenter
    private lateinit var myRetroCryptoArrayList: List<Model.User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.contact_list_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactListPresenter = ContactListPresenter(this)
        contactListPresenter.fetchContacts()
    }
    override fun fetchContacts() {
        progress_horizontal.visibility = View.VISIBLE
        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GetData::class.java)
        requestInterface.getContacts("1").enqueue(object : Callback<Model.ContactResponse> {
            override fun onFailure(call: Call<Model.ContactResponse>, t: Throwable) {
                contactListPresenter.handleError(t)
            }
            override fun onResponse(call: Call<Model.ContactResponse>, response: Response<Model.ContactResponse>) {
                contactListPresenter.handleResponse(response.body())
            }
        })
    }
    override fun handleResponse(contactResponse: Model.ContactResponse?) {
        myRetroCryptoArrayList = contactResponse?.data!!
        val myAdapter = ContactListAdapter(myRetroCryptoArrayList)
        rcyContactList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcyContactList.adapter = myAdapter
        progress_horizontal.visibility = View.GONE
    }
    override fun handleError(error: Throwable) {
        error.printStackTrace()
        progress_horizontal.visibility = View.GONE
    }
}
