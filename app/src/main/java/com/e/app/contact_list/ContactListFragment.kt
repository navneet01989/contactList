package com.e.app.contact_list

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.app.R
import com.e.app.adapters.ContactListAdapter
import com.e.app.api.ApiWorker
import com.e.app.models.RetroCrypto
import com.e.app.api.GetData
import com.e.app.api.GetData.Companion.BASE_URL
import com.e.app.models.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.contact_list_fragment.view.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class ContactListFragment: Fragment(), ContactListPresenter.View {
    private lateinit var progressDialog: ProgressDialog;
    private lateinit var contactListPresenter: ContactListPresenter
    private var myCompositeDisposable: CompositeDisposable? = null
    private var myRetroCryptoArrayList: List<User>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.contact_list_fragment, container, false)
        view.rcyContactList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        contactListPresenter = ContactListPresenter(this)
        contactListPresenter.fetchContacts()
        myCompositeDisposable = CompositeDisposable()
        return view
    }
    override fun fetchContacts() {
        try {
            val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ApiWorker.gsonConverter)
                .client(ApiWorker.client)
                .build().create(GetData::class.java)

            myCompositeDisposable?.add(requestInterface.getContacts("1")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {result -> Log.d("result", result.toString()) },
                    {error -> error.printStackTrace()}
                ))
        } catch(ex:Exception){
            ex.printStackTrace()
        }
        progressDialog = ProgressDialog.show(context, "wait", "requesting");
    }
    private fun handleResponse(cryptoList: RetroCrypto) {
        progressDialog.dismiss()
        myRetroCryptoArrayList = cryptoList.data
        val myAdapter = ContactListAdapter(myRetroCryptoArrayList!!)
        view?.rcyContactList?.adapter = myAdapter
    }
    private fun handleError(error: Throwable) {
        error.printStackTrace()
        progressDialog.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.dispose()
    }
}