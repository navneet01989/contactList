package com.e.app.details

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.e.app.R
import com.e.app.edit_details.EditDetailsFragment
import com.e.app.models.Model
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.contact_details_fragment.*
import kotlinx.android.synthetic.main.contact_details_fragment.view.*
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactDetailsFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.getSerializable("user") as Model.User
        view.txtName.text = String.format("%s %s", user.first_name, user.last_name)
        view.txtMobile.text = user.id.toString()
        Picasso.get()
            .load(user.avatar)
            .placeholder(R.drawable.ic_user_icon)
            .error(R.drawable.ic_user_icon)
            .into(view.avatar_detail)
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if( keyCode == KeyEvent.KEYCODE_BACK ) {
                fragmentManager?.popBackStack()
                return@setOnKeyListener true
            }
            false
        }
        view.txtBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        view.txtEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", user)
            val editDetailsFragment = EditDetailsFragment()
            editDetailsFragment.arguments = bundle
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.main_frame, editDetailsFragment, "editDetailsFragment")
                ?.addToBackStack("editDetailsFragment")
                ?.commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        LayoutInflater.from(context)
            .inflate(R.layout.contact_details_fragment, container, false)!!
}