package com.huawei.podcast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huawei.podcast.R
import com.huawei.podcast.data.model.EpisodeList
import com.huawei.podcast.databinding.ItemEpisodeBinding
import com.huawei.podcast.utils.EpisodeClickListener

class EpisodeAdapter(val clickListener: EpisodeClickListener) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    var eList: List<EpisodeList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val viewBinding: ItemEpisodeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_episode, parent, false
        )
        return EpisodeViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return eList.size
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.onBind(position)
    }



    inner class EpisodeViewHolder(private val viewBinding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int) {
            val row = eList[position]
            viewBinding.eList = row
            viewBinding.clickInterface = clickListener
            viewBinding.position = position
        }
    }

    fun setList(eList: List<EpisodeList>) {
        this.eList = eList
        notifyDataSetChanged()
    }

}
