package com.huawei.podcast.ui.main.view


import android.app.Dialog
import android.content.Intent
import com.huawei.podcast.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.podcast.data.model.CategoryCollection
import com.huawei.podcast.data.model.CategoryModel
import com.huawei.podcast.data.model.SubCategoryCollection
import com.huawei.podcast.databinding.FragmentHomeBinding
import com.huawei.podcast.ui.main.adapter.CategoryAdapter
import com.huawei.podcast.ui.main.adapter.HomeAdapter
import com.huawei.podcast.ui.main.viewmodel.HomeViewModel
import com.huawei.podcast.utils.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() , CategoryClickListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var adapter: HomeAdapter
    private lateinit var cAdapter: CategoryAdapter
    lateinit var dialog: Dialog
    lateinit var fragmentHomeBindingImpl: FragmentHomeBinding
    private lateinit var cList: CategoryModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentHomeBindingImpl = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val view: View = fragmentHomeBindingImpl.root
        fragmentHomeBindingImpl.viewModel = homeViewModel
        fragmentHomeBindingImpl.lifecycleOwner = this
        dialog = ProgressDialog.showProgress(this.requireActivity())
        setupUI(view)
        setupObserver()
        return view
    }

    private fun setupUI(view: View) {
        /*trending*/
        view.rv_trending.layoutManager =
            LinearLayoutManager(this.requireActivity()).also {
                view.rv_trending.layoutManager = it
                it.orientation = LinearLayoutManager.HORIZONTAL
            }

        adapter = HomeAdapter(this)
        view.rv_trending.adapter = adapter
        /*category*/
        view.rv_category.layoutManager =
            LinearLayoutManager(this.requireActivity()).also {
                view.rv_category.layoutManager = it
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
        view.rv_category.adapter = adapter
        /*sub category*/
        view.rv_sub_category.layoutManager =
            LinearLayoutManager(this.requireActivity()).also {
                view.rv_sub_category.layoutManager = it
                it.orientation = LinearLayoutManager.HORIZONTAL
            }

        cAdapter = CategoryAdapter(this)
        view.rv_sub_category.adapter = cAdapter
        /*interest*/


    }

    private fun setupObserver() {
        homeViewModel.pList.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    it.data?.let { cList -> renderList(cList) }
                }
                Status.LOADING -> {
                    dialog.show()
                }
                Status.ERROR -> {
                    //Handle Error
                    dialog.dismiss()
                    Toast.makeText(this.requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(pList: CategoryModel) {
        cList = pList
        pList.collection?.let { adapter.setList(it) }
        pList.collection?.get(0)?.subcategories?.collection?.let { cAdapter.setList(it) }
        adapter.notifyDataSetChanged()
        cAdapter.notifyDataSetChanged()
    }

    override fun onItemCategoryClick(category: CategoryCollection,position : Int) {
        cList.collection?.get(position)?.subcategories?.collection?.let { cAdapter.setList(it) }
        cAdapter.notifyDataSetChanged()
    }


    override fun onItemClick(category: SubCategoryCollection) {
        val i = Intent(this.requireActivity(), DetailsActivity::class.java)
        startActivity(i)
    }


}