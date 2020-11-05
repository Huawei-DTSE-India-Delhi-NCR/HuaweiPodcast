package com.huawei.podcast.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huawei.podcast.R
import com.huawei.podcast.data.model.PodCastList
import com.huawei.podcast.databinding.ItemEpisodeBinding
import com.huawei.podcast.utils.ClickListener

class EpisodeAdapter(val clickListener: ClickListener) : RecyclerView.Adapter<EpisodeAdapter.episodeViewHolder>() {

    var pList: List<PodCastList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): episodeViewHolder {
        val viewBinding: ItemEpisodeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_episode, parent, false
        )
        return episodeViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return pList.size
    }

    override fun onBindViewHolder(holder: episodeViewHolder, position: Int) {
        holder.onBind(position)
    }



    inner class episodeViewHolder(private val viewBinding: ItemEpisodeBinding) :
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
