package com.daniel.memberid

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(_context: Context) {
    private var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor

   companion object{
       private const val PRIVATE_MODE = 0
       private const val PREF_NAME = "member"


   }

    val isLogin: Boolean
        get() = preferences.getBoolean("isLogin", false)

    init {
        preferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = preferences.edit()
        editor.apply()
    }

    fun createSessionBoolean(name: String, status: Boolean) {
        editor.putBoolean(name, status)
        editor.commit()
    }

    fun logout() {
        editor.remove("isLogin")
        editor.commit()
    }

    fun setLoggedIn() {
        createSessionBoolean("isLogin", true)
    }


}