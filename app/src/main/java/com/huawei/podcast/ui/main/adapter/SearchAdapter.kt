package com.huawei.podcast.ui.main.adapter


import android.content.Context
import android.content.Intent
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.huawei.podcast.R
import com.huawei.podcast.ui.main.view.DetailsActivity

import kotlinx.android.synthetic.main.item_search.view.*
import java.util.*
import kotlin.collections.ArrayList

class SearchAdapter(private var categoryList: ArrayList<String>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var categoryFilterList = ArrayList<String>()
    lateinit var mcontext: Context


    class CountryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        categoryFilterList = categoryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val countryListView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        val sch = CountryHolder(countryListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return categoryFilterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.txt_search.text = categoryFilterList[position]
        holder.itemView.setOnClickListener {
            val i = Intent(mcontext, DetailsActivity::class.java)
            mcontext.startActivity(i)
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                categoryFilterList = if (charSearch.isEmpty()) {
                    categoryList
                } else {
                    val resultList = ArrayList<String>()
                    for (row in categoryList) {
                        if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = categoryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                categoryFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }

    fun setList(sList: ArrayList<String>) {
        categoryFilterList = sList
        notifyDataSetChanged()
    }


}