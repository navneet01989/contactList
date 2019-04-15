package com.e.app.edit_details

import com.e.app.models.Model

class EditDetailsPresenter {
    interface View {
        fun EditProfile()
        fun handleResponse(contactResponse: Model.ContactResponse?)
        fun handleError(error: Throwable)
    }
}