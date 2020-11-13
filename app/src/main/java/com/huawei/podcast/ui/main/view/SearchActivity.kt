package com.huawei.podcast.ui.main.view

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.podcast.R
import com.huawei.podcast.data.model.CategoryModel
import com.huawei.podcast.databinding.ActivitySearchBinding
import com.huawei.podcast.ui.main.adapter.SearchAdapter
import com.huawei.podcast.ui.main.viewmodel.HomeViewModel
import com.huawei.podcast.utils.ProgressDialog
import com.huawei.podcast.utils.Status
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.include_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    lateinit var activitySearchBinding: ActivitySearchBinding
    private lateinit var adapter: SearchAdapter
    lateinit var dialog: Dialog
    var categoryFilterList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        activitySearchBinding.viewModel = homeViewModel
        activitySearchBinding.lifecycleOwner = this
        dialog = ProgressDialog.showProgress(this)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        rv_search.layoutManager = LinearLayoutManager(this).also { rv_search.layoutManager = it }
        adapter = SearchAdapter(categoryFilterList)
        rv_search.adapter = adapter
        activitySearchBinding.inHeader.imgBackArrow.setOnClickListener {
            onBackPressed()
        }
        pod_cast_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

    }

    private fun setupObserver() {
        homeViewModel.pList.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    it.data?.let { sList -> renderList(sList) }
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

    private fun renderList(sList: CategoryModel) {
        for (i in sList.collection?.indices!!) {
            sList.collection[i].label?.let {
                categoryFilterList.add(it)
            }
        }
        adapter.setList(categoryFilterList)
        adapter.notifyDataSetChanged()
    }

}