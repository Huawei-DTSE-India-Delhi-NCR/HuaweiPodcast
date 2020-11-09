package com.huawei.podcast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huawei.podcast.R
import com.huawei.podcast.data.model.CategoryCollection
import com.huawei.podcast.databinding.ItemHomeBinding
import com.huawei.podcast.utils.CategoryClickListener


class HomeAdapter(val clickListener: CategoryClickListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var pList: List<CategoryCollection> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val viewBinding: ItemHomeBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_home, parent, false
        )
        return HomeViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return pList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(position)
    }



    inner class HomeViewHolder(private val viewBinding: ItemHomeBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int) {
            val row = pList[position]
            viewBinding.pList = row
            viewBinding.position = position
            viewBinding.clickInterface = clickListener
        }
    }

    fun setList(podCastList: List<CategoryCollection>) {
        this.pList = podCastList
        notifyDataSetChanged()
    }

}
