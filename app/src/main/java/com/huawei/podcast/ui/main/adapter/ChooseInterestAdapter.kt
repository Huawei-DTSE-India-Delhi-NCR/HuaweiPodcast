package com.huawei.podcast.ui.main.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.huawei.podcast.R
import com.huawei.podcast.data.model.PodCastList
import com.huawei.podcast.databinding.ItemChooseInterestBinding
import com.huawei.podcast.utils.ClickListener


class ChooseInterestAdapter(val clickListener: ClickListener) : RecyclerView.Adapter<ChooseInterestAdapter.ChooseInterestViewHolder>() {

    var pList: List<PodCastList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseInterestViewHolder {
        val viewBinding: ItemChooseInterestBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_choose_interest, parent, false
        )
        return ChooseInterestViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return pList.size
    }

    override fun onBindViewHolder(holder: ChooseInterestViewHolder, position: Int) {
        holder.onBind(position)
    }



    inner class ChooseInterestViewHolder(private val viewBinding: ItemChooseInterestBinding) :
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


