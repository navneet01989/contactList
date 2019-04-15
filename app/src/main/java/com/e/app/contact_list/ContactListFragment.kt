package com.e.app.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import androidx.recyclerview.widget.DividerItemDecoration
import com.e.app.R
import kotlinx.android.synthetic.main.contact_list_fragment.view.*

class ContactListFragment: Fragment(), ContactListPresenter.View {

    private lateinit var viewOfLayout: View
    private lateinit var contactListPresenter: ContactListPresenter
    private var myRetroCryptoArrayList: MutableList<Model.User>? = null
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading = false
    private val PAGE_SIZE = 10
    private var isLastPage = false
    private var currentPage = 1
    private lateinit var myAdapter: ContactListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewOfLayout = inflater.inflate(R.layout.contact_list_fragment, container, false)
        return viewOfLayout
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactListPresenter = ContactListPresenter(this)
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(
            context,
            RecyclerView.VERTICAL
        )
        viewOfLayout.rcyContactList.layoutManager = layoutManager
        viewOfLayout.rcyContactList.addItemDecoration(dividerItemDecoration)
        viewOfLayout.rcyContactList.addOnScrollListener(recyclerViewOnScrollListener)
        if(myRetroCryptoArrayList == null || myRetroCryptoArrayList!!.size == 0) {
            contactListPresenter.fetchContacts()
        } else {
            myAdapter = ContactListAdapter(myRetroCryptoArrayList!!, this)
            viewOfLayout.rcyContactList.adapter = myAdapter
        }
        emptyView.setOnClickListener {
            isLastPage = false
            currentPage = 1
            viewOfLayout.emptyView.visibility = View.GONE
            viewOfLayout.rcyContactList.visibility = View.VISIBLE
            contactListPresenter.fetchContacts()
        }
    }
    override fun fetchContacts() {
        isLoading = true
        progress_horizontal.visibility = View.VISIBLE
        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GetData::class.java)
        requestInterface.getContacts(currentPage, PAGE_SIZE).enqueue(object : Callback<Model.ContactResponse> {
            override fun onFailure(call: Call<Model.ContactResponse>, t: Throwable) {
                contactListPresenter.handleError(t)
            }
            override fun onResponse(call: Call<Model.ContactResponse>, response: Response<Model.ContactResponse>) {
                if(response.code() == 200) {
                    contactListPresenter.handleResponse(response.body())
                } else if(myRetroCryptoArrayList == null || myRetroCryptoArrayList?.size == 0) {
                    viewOfLayout.emptyView.visibility = View.VISIBLE
                    viewOfLayout.rcyContactList.visibility = View.GONE
                }
            }
        })
    }
    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            if(!isLoading && !isLastPage) {
                if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                    currentPage++
                    contactListPresenter.fetchContacts()
                }
            }
        }
    }
    override fun handleResponse(contactResponse: Model.ContactResponse?) {
        if(currentPage == 1) {
            if(contactResponse?.data!!.size == 0) {
                isLastPage = true
            } else {
                myRetroCryptoArrayList = contactResponse.data
                myAdapter = ContactListAdapter(myRetroCryptoArrayList!!, this)
                viewOfLayout.rcyContactList.adapter = myAdapter
            }
        } else {
            if(contactResponse?.data!!.size == 0) {
                isLastPage = true
            } else {
                myRetroCryptoArrayList?.addAll(contactResponse.data)
                myAdapter.notifyDataSetChanged()
            }
        }
        viewOfLayout.progress_horizontal.visibility = View.GONE
        isLoading = false
    }
    override fun handleError(error: Throwable) {
        error.printStackTrace()
        viewOfLayout.progress_horizontal.visibility = View.GONE
        isLoading = false
        if(myRetroCryptoArrayList == null || myRetroCryptoArrayList?.size == 0) {
            viewOfLayout.emptyView.visibility = View.VISIBLE
            viewOfLayout.rcyContactList.visibility = View.GONE
        }
    }
}
