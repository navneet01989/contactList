package com.e.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.e.app.contact_list.ContactListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(frame_layout.id, ContactListFragment()).commitAllowingStateLoss()
    }
}
