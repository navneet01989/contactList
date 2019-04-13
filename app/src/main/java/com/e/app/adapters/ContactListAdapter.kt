package com.e.app.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.e.app.R
import com.e.app.details.ContactDetailsFragment
import com.e.app.models.Model
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactListAdapter(
    private val myRetroCryptoArrayList: List<Model.User>,
    private val contactListFragment: Fragment) : RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    class ViewHolder(private val view : View, private val contactListFragment: Fragment) : RecyclerView.ViewHolder(view) {
        fun bind(users: List<Model.User>, position: Int) {
            val user = users[position]
            view.txtContactName.text = String.format("%s %s", user.first_name, user.last_name)
            Picasso.get()
                .load(user.avatar)
                .placeholder(R.drawable.ic_user_icon)
                .error(R.drawable.ic_user_icon)
                .into(view.avatar)
            view.setOnClickListener {
                val contactDetailsFragment = ContactDetailsFragment()
                val bundle = Bundle()
                bundle.putSerializable("user", users[position])
                bundle.putInt("position", position)
                contactDetailsFragment.arguments = bundle
                contactListFragment
                    .fragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.main_frame, contactDetailsFragment, "ContactDetailsFragment")
                    ?.addToBackStack("ContactDetailsFragment")
                    ?.commit()
            }
        }
    }
    override fun getItemCount() = myRetroCryptoArrayList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myRetroCryptoArrayList, position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false), contactListFragment)
}