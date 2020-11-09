package com.huawei.podcast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huawei.podcast.R
import com.huawei.podcast.data.model.SubCategoryCollection
import com.huawei.podcast.databinding.ItemCategoryBinding
import com.huawei.podcast.utils.CategoryClickListener

class InterestAdapter(val clickListener: CategoryClickListener) : RecyclerView.Adapter<InterestAdapter.CategoryViewHolder>() {

    var pList: List<SubCategoryCollection> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val viewBinding: ItemCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category, parent, false
        )
        return CategoryViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return pList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(position)
    }



    inner class CategoryViewHolder(private val viewBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int) {
            val row = pList[position]
            viewBinding.pList = row
            viewBinding.clickInterface = clickListener
        }
    }

    fun setList(cList: List<SubCategoryCollection>) {
        this.pList = cList
        notifyDataSetChanged()
    }

}
