package com.e.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.app.R
import com.e.app.models.User
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactListAdapter(val myRetroCryptoArrayList: List<User>) : RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    override fun getItemCount() = myRetroCryptoArrayList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myRetroCryptoArrayList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(user: User) {
            view.txtContactName.text = user.first_name + user.last_name
        }
    }
}