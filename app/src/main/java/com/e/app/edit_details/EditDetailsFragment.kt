package com.e.app.edit_details

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.e.app.R
import com.e.app.models.Model
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.edit_details_fragment.view.*

class EditDetailsFragment: Fragment(), EditDetailsPresenter.View {
    override fun handleError(error: Throwable) {

    }

    override fun handleResponse(contactResponse: Model.ContactResponse?) {

    }

    override fun EditProfile() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.getSerializable("user") as Model.User
        Picasso.get()
            .load(user.avatar)
            .placeholder(R.drawable.ic_user_icon)
            .error(R.drawable.ic_user_icon)
            .into(view.avatar_Detail_edit)
        view.txtFirstName.setText(user.first_name)
        view.txtLastName.setText(user.last_name)
        view.txtID.text = user.id.toString()
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if( keyCode == KeyEvent.KEYCODE_BACK ) {
                fragmentManager?.popBackStack()
                return@setOnKeyListener true
            }
            false
        }
        view.txtBackDetailEdit.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        LayoutInflater.from(context).inflate(R.layout.edit_details_fragment, container, false)!!
}