package com.daniel.memberid

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(_context: Context) {
    private var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "member"

    val is_login: Boolean
        get() = preferences.getBoolean("is_login", false)


    init {
        preferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = preferences.edit()
    }

    fun createSessionBoolean(name: String, status: Boolean) {
        editor.putBoolean(name, status)
        editor.commit()
    }

    fun logout() {
        editor.remove("is_login")
        editor.commit()
    }

    fun setLoggedIn() {
        createSessionBoolean("is_login", true)
    }


}