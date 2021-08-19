package com.example.filterhighlightdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private val bookList: List<BookModel> = listOf(
        BookModel("Hello world"),
        BookModel("Hello kitty"),
        BookModel("Helen of Troy"),
        BookModel("Roy and Mick"),
        BookModel("Kitkat heaven"),
        BookModel("Hello hello"),
    )
    private val bookAdapter = BookAdapter(bookList, object : BookAdapter.OnItemClickListener {
        override fun onItemClick(book: BookModel) {
            Toast.makeText(this@MainActivity, "clicked ${book.bookName}", Toast.LENGTH_SHORT).show()
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchView: SearchView = findViewById(R.id.search_view)
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
        val recyclerView: RecyclerView = findViewById(R.id.rv_books)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookAdapter
    }

    private fun filter(text: String) {
        val filteredList = mutableListOf<BookModel>()

        for (item in bookList) {
            if (item.bookName.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            bookAdapter.filterList(filteredList, text)
        }
    }
}