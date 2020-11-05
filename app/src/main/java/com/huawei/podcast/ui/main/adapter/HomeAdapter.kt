package com.huawei.podcast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huawei.podcast.R
import com.huawei.podcast.data.model.PodCastList
import com.huawei.podcast.databinding.ItemChooseInterestBinding
import com.huawei.podcast.databinding.ItemHomeBinding
import com.huawei.podcast.utils.ClickListener

class HomeAdapter(val clickListener: ClickListener) : RecyclerView.Adapter<HomeAdapter.homeViewHolder>() {

    var pList: List<PodCastList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): homeViewHolder {
        val viewBinding: ItemHomeBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_home, parent, false
        )
        return homeViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return pList.size
    }

    override fun onBindViewHolder(holder: homeViewHolder, position: Int) {
        holder.onBind(position)
    }



    inner class homeViewHolder(private val viewBinding: ItemHomeBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int) {
            val row = pList[position]
            viewBinding.pList = row
            viewBinding.clickInterface = clickListener
        }
    }

    fun setList(podCastList: List<PodCastList>) {
        this.pList = podCastList
        notifyDataSetChanged()
    }

}
