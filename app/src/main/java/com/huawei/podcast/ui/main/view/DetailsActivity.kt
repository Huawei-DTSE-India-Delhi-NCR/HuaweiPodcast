package com.huawei.podcast.ui.main.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.podcast.R
import com.huawei.podcast.data.model.EpisodeList
import com.huawei.podcast.data.model.EpisodeModel
import com.huawei.podcast.databinding.ActivityDetailsBinding
import com.huawei.podcast.ui.main.adapter.EpisodeAdapter
import com.huawei.podcast.ui.main.viewmodel.DetailsViewModel
import com.huawei.podcast.utils.EpisodeClickListener
import com.huawei.podcast.utils.ProgressDialog
import com.huawei.podcast.utils.Status
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity(), EpisodeClickListener {
    private val detailsViewModel: DetailsViewModel by viewModel()
    private lateinit var adapter: EpisodeAdapter
    lateinit var dialog: Dialog
    lateinit var activityDetailsBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_details)
        activityDetailsBinding.viewModel = detailsViewModel
        activityDetailsBinding.lifecycleOwner = this
        dialog = ProgressDialog.showProgress(this)
        setupUI()
        setupObserver()

    }

    private fun setupUI() {
        rv_episodes.layoutManager =
            LinearLayoutManager(this).also { rv_episodes.layoutManager = it }
        adapter = EpisodeAdapter(this)
        rv_episodes.adapter = adapter
        activityDetailsBinding.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupObserver() {
        detailsViewModel.pList.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    it.data?.let { eList -> renderList(eList) }
                }
                Status.LOADING -> {
                    dialog.show()
                }
                Status.ERROR -> {
                    //Handle Error
                    dialog.dismiss()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(eList: EpisodeModel) {
        eList.collection?.let { adapter.setList(it) }
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(episode: EpisodeList) {
        val i = Intent(this, EpisodeDetailsActivity::class.java)
         i.putExtra("episode_List", episode)
         startActivity(i)
    }


}


