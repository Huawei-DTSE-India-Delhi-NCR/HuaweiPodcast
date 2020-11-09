package com.huawei.podcast.ui.main.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.huawei.podcast.R
import com.huawei.podcast.data.model.CategoryCollection
import com.huawei.podcast.data.model.CategoryModel
import com.huawei.podcast.databinding.ActivityChooseYourInterestBinding
import com.huawei.podcast.ui.main.adapter.ChooseInterestAdapter
import com.huawei.podcast.ui.main.viewmodel.ChooseInterestViewModel
import com.huawei.podcast.utils.ChooseInterestClickListener
import com.huawei.podcast.utils.ProgressDialog
import com.huawei.podcast.utils.Status
import kotlinx.android.synthetic.main.activity_choose_your_interest.*
import org.koin.android.viewmodel.ext.android.viewModel


class ChooseInterest : AppCompatActivity(), ChooseInterestClickListener {

    private val chooseViewModel: ChooseInterestViewModel by viewModel()
    private lateinit var adapter: ChooseInterestAdapter
    lateinit var dialog: Dialog
    lateinit  var activityChooseYourInterestBinding: ActivityChooseYourInterestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityChooseYourInterestBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_your_interest)
        activityChooseYourInterestBinding.viewModel = chooseViewModel
        activityChooseYourInterestBinding.lifecycleOwner = this
        dialog = ProgressDialog.showProgress(this)
        setupUI()
        setupObserver()

    }

    private fun setupUI() {
        rv_interest.layoutManager = GridLayoutManager(this, 2).also { rv_interest.layoutManager = it }
        adapter = ChooseInterestAdapter(this)
        rv_interest.adapter = adapter
        activityChooseYourInterestBinding.inHeader.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupObserver() {
        chooseViewModel.pList.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    it.data?.let {
                            users -> renderList(users) }
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

    private fun renderList(pList: CategoryModel) {
        pList.collection?.let { adapter.setList(it) }
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(category: CategoryCollection) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }



}
