package com.example.kotlin_search

import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.*
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kotlin_search.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.queryHint = getString(R.string.search)
        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1

        val countryList = mutableListOf(
            CountryItem(0, "Afghanistan", android.R.drawable.ic_delete),
            CountryItem(1, "Bangladesh", android.R.drawable.ic_btn_speak_now),
            CountryItem(2, "China", android.R.drawable.ic_menu_report_image),
            CountryItem(3, "India", null),
            CountryItem(4, "Japan", android.R.drawable.ic_dialog_dialer),
            CountryItem(5, "Nepal", android.R.drawable.ic_dialog_email),
            CountryItem(6, "North Korea", null),
            CountryItem(7, "South Korea", android.R.drawable.ic_dialog_map),
            CountryItem(8, "Srilanka", android.R.drawable.ic_input_add),
            CountryItem(9, "Pakistan", android.R.drawable.ic_input_delete)
        )

        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, "title", "image"))
        countryList.forEach {
            cursor.addRow(arrayOf(it._id, it.title, it.image))
        }
        val from = arrayOf("title", "image")
        val to = intArrayOf(R.id.label, R.id.pic)
        val cursorAdapter = CustomSimpleCursorAdapter(context, R.layout.custom_layout, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)

        searchView.suggestionsAdapter = cursorAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val newCursor = MatrixCursor(arrayOf(BaseColumns._ID, "title", "image"))
                query?.let {
                    countryList.forEachIndexed { index, suggestion ->
                        if (suggestion.title.contains(query, true))
                            newCursor.addRow(arrayOf(index, suggestion.title, suggestion.image))
                    }
                }

                cursorAdapter.changeCursor(newCursor)
                return true
            }
        })

        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex("title"))
                searchView.setQuery(selection, false)

                // Do something with selection
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)
    }
}
