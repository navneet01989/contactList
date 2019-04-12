package com.e.app.contact_list

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.app.R
import com.e.app.adapters.ContactListAdapter
import com.e.app.api.ApiWorker
import com.e.app.api.GetData
import com.e.app.api.GetData.Companion.BASE_URL
import com.e.app.models.Model
import kotlinx.android.synthetic.main.contact_list_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ContactListFragment: Fragment(), ContactListPresenter.View {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var contactListPresenter: ContactListPresenter
    private var myRetroCryptoArrayList: List<Model.User>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.contact_list_fragment, container, false)
        view.rcyContactList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        contactListPresenter = ContactListPresenter(this)
        contactListPresenter.fetchContacts()
        return view
    }
    override fun fetchContacts() {
        try {
            progressDialog = ProgressDialog.show(context, "wait", "requesting")
            val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ApiWorker.gsonConverter)
                .client(ApiWorker.client)
                .build().create(GetData::class.java)
            requestInterface.getContacts("1").enqueue(object : Callback<Model.RetroCrypto> {
                override fun onFailure(call: Call<Model.RetroCrypto>, t: Throwable) {
                    handleError(t)
                }

                override fun onResponse(call: Call<Model.RetroCrypto>, response: Response<Model.RetroCrypto>) {
                    handleResponse(response.body())
                }

            })

        } catch(ex:Exception){
            ex.printStackTrace()
        }
    }
    private fun handleResponse(cryptoList: Model.RetroCrypto?) {
        myRetroCryptoArrayList = cryptoList?.data
        val myAdapter = ContactListAdapter(myRetroCryptoArrayList!!)
        view?.rcyContactList?.adapter = myAdapter
        progressDialog.dismiss()
    }
    private fun handleError(error: Throwable) {
        error.printStackTrace()
        progressDialog.dismiss()
    }
}
