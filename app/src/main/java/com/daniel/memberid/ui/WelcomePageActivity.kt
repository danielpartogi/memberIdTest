package com.daniel.memberid.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daniel.memberid.R
import com.daniel.memberid.SharedPreference
import com.daniel.memberid.databinding.ActivityWelcomePageBinding
import com.daniel.memberid.viewModel.WelcomePageViewModel



class WelcomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomePageBinding
    private lateinit var viewModel: WelcomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        val sharedPreference = SharedPreference(this)
        if(sharedPreference.isLogin){
            gotoFeedPage()
        }

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_welcome_page)
        viewModel = ViewModelProviders.of(this).get(WelcomePageViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.error.observe(this, Observer {
            if(!it){
                gotoFeedPage()
                sharedPreference.setLoggedIn()
            }
        })

        binding.textInput.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                            viewModel.login()
                            return true
                        }
                        else -> {
                        }
                    }
                }
                return false
            }
        })
    }

    private fun gotoFeedPage(){
        val intent = Intent(this, FeedPageActivity::class.java)
        startActivity(intent)
        finish()
    }
}