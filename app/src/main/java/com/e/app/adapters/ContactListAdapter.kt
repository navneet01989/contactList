package com.e.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.app.R
import com.e.app.models.Model
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactListAdapter(val myRetroCryptoArrayList: List<Model.User>) : RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    class ViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        fun bind(user: Model.User) {
            view.txtContactName.text = String.format("%s %s", user.first_name, user.last_name)
            Picasso.get()
                .load(user.avatar)
                .placeholder(R.drawable.ic_user_icon)
                .error(R.drawable.ic_user_icon)
                .into(view.avatar)
        }
    }
    override fun getItemCount() = myRetroCryptoArrayList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myRetroCryptoArrayList[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false))
}