package com.example.filterhighlightdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(
    private var booklist: List<BookModel>, private val itemClickListener:
    OnItemClickListener
) : RecyclerView.Adapter<BookAdapter.BookHolder>() {

    var searchText: String = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookHolder(view)
    }

    fun filterList(filterList: List<BookModel>, searchText: String) {
        this.searchText = searchText
        booklist = filterList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val view = booklist[position]

        holder.bind(view, itemClickListener)


    }

    override fun getItemCount(): Int {
        return booklist.size
    }

    inner class BookHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bookNameTextView: TextView = view.findViewById(R.id.tv_book_name)

        fun bind(book: BookModel, clickListener: OnItemClickListener) {
            if (searchText.isNotBlank()) {
                val highlightedText = book.bookName.replace(searchText, "<font color='red'>$searchText</font>", true)
                bookNameTextView.text = HtmlCompat.fromHtml(highlightedText, HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                bookNameTextView.text = book.bookName
            }
            itemView.setOnClickListener {
                clickListener.onItemClick(book)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(book: BookModel)
    }
}