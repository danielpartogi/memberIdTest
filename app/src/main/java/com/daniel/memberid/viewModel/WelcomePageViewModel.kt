package com.daniel.memberid.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.daniel.memberid.SingleLiveEvent

class WelcomePageViewModel(application: Application) : AndroidViewModel(application) {

     val email: ObservableField<String> = ObservableField("")
     val error: SingleLiveEvent<Boolean> = SingleLiveEvent()
     val login : ObservableField<Boolean> = ObservableField(false)

    fun login(){
        if(email.get()!="danielpartogi1996@gmail.com"){
            error.value = true
        }
        else {
            login.set(true)
            error.value = false
        }
    }

}