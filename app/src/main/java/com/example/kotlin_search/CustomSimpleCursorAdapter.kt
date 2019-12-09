package com.example.kotlin_search

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.database.getIntOrNull
import androidx.cursoradapter.widget.SimpleCursorAdapter

class CustomSimpleCursorAdapter(
    private val context: Context?,
    @LayoutRes private val layout: Int,
    private val c: Cursor?,
    private val from: Array<String>,
    private val to: IntArray,
    flags: Int
) : SimpleCursorAdapter(context, layout, c, from, to, flags) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v = convertView
        if (v == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater
            v = inflater?.inflate(layout, null)
        }
        c?.moveToPosition(position)
        val title = c?.getString(c.getColumnIndex(from[0]))
        val image = c?.getIntOrNull(c.getColumnIndex(from[1]))
        val iv = v!!.findViewById(to[1]) as? ImageView
        if (image != null) {
            iv?.setImageResource(image)
        } else {
            iv?.setImageResource(R.drawable.ic_image_black_24dp)
        }
        val fname = v.findViewById(to[0]) as? TextView
        fname?.text = title
        return v
    }
}
