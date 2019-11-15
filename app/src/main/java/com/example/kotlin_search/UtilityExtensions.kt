package com.example.kotlin_search

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.setupActionBar(title: String, displayHome: Boolean = false, hasOptionsMenu: Boolean = false) {
    setHasOptionsMenu(hasOptionsMenu)
    (activity as? AppCompatActivity)?.invalidateOptionsMenu()
    (activity as? AppCompatActivity)?.supportActionBar?.apply {
        this.title = title
        setDisplayShowHomeEnabled(displayHome)
        setDisplayHomeAsUpEnabled(displayHome)
        this.show()
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}
