package com.huawei.podcast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huawei.podcast.R
import com.huawei.podcast.data.model.TrendingModel
import com.huawei.podcast.databinding.ItemTrendingBinding
import com.huawei.podcast.utils.CategoryClickListener


class TrendingAdapter(val clickListener: CategoryClickListener) : RecyclerView.Adapter<TrendingAdapter.HomeViewHolder>() {

    var pList: List<TrendingModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val viewBinding: ItemTrendingBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_trending, parent, false
        )
        return HomeViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return pList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.onBind(position)
    }



    inner class HomeViewHolder(private val viewBinding: ItemTrendingBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int) {
            val row = pList[position]
            viewBinding.pList = row
            viewBinding.position = position
            viewBinding.clickInterface = clickListener
        }
    }

    fun setList(podCastList: List<TrendingModel>) {
        this.pList = podCastList
        notifyDataSetChanged()
    }

}
