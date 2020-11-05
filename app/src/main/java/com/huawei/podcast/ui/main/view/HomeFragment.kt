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
import com.huawei.podcast.data.model.PodCastList
import com.huawei.podcast.databinding.FragmentHomeBinding
import com.huawei.podcast.ui.main.adapter.HomeAdapter
import com.huawei.podcast.ui.main.viewmodel.HomeViewModel
import com.huawei.podcast.utils.ClickListener
import com.huawei.podcast.utils.ProgressDialog
import com.huawei.podcast.utils.Status
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() , ClickListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var adapter: HomeAdapter
    lateinit var dialog: Dialog
    lateinit var fragmentHomeBindingImpl: FragmentHomeBinding

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
        val catMLayoutManager = LinearLayoutManager(this.requireActivity())
        catMLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        view.rv_category.layoutManager = catMLayoutManager
        view.rv_category.adapter = adapter
        /*interest*/
        val intMLayoutManager = LinearLayoutManager(this.requireActivity())
        intMLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        view.rv_your_interest.layoutManager = intMLayoutManager
        view.rv_your_interest.adapter = adapter

    }

    private fun setupObserver() {
        homeViewModel.pList.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    it.data?.let { users -> renderList(users) }
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

    private fun renderList(pList: List<PodCastList>) {
        adapter.setList(pList)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(country: PodCastList) {
        val i = Intent(this.requireActivity(), DetailsActivity::class.java)
        startActivity(i)
    }


}