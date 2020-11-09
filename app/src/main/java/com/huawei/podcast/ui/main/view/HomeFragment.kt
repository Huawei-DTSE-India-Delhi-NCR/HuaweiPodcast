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
import com.huawei.podcast.data.model.TrendingModel
import com.huawei.podcast.databinding.FragmentHomeBinding
import com.huawei.podcast.ui.main.adapter.CategoryAdapter
import com.huawei.podcast.ui.main.adapter.HomeAdapter
import com.huawei.podcast.ui.main.adapter.InterestAdapter
import com.huawei.podcast.ui.main.adapter.TrendingAdapter
import com.huawei.podcast.ui.main.viewmodel.HomeViewModel
import com.huawei.podcast.utils.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() , CategoryClickListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var adapter: HomeAdapter
    private lateinit var cAdapter: CategoryAdapter
    private lateinit var iAdapter: InterestAdapter
    private lateinit var tAdapter: TrendingAdapter
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
        tAdapter = TrendingAdapter(this)
        view.rv_trending.adapter = tAdapter
        trending()

        /*category*/
        view.rv_category.layoutManager =
            LinearLayoutManager(this.requireActivity()).also {
                view.rv_category.layoutManager = it
                it.orientation = LinearLayoutManager.HORIZONTAL
            }
        adapter = HomeAdapter(this)
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
        view.rv_interest.layoutManager =
                LinearLayoutManager(this.requireActivity()).also {
                    view.rv_interest.layoutManager = it
                    it.orientation = LinearLayoutManager.HORIZONTAL
                }
        iAdapter = InterestAdapter(this)
        view.rv_interest.adapter = iAdapter

    }

    private fun trending(){
        val tList = ArrayList<TrendingModel>()
        tList.add(TrendingModel("Books"))
        tList.add(TrendingModel("Careers"))
        tList.add(TrendingModel("Stand Up"))
        tList.add(TrendingModel("Language Learning"))
        tList.add(TrendingModel("Drama"))
        tList.add(TrendingModel("Music History"))
        tList.add(TrendingModel("Dialy News"))
        tAdapter.setList(tList)
        tAdapter.notifyDataSetChanged()
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
        chooseYourInterest(cList)
        pList.collection?.let { adapter.setList(it) }
        pList.collection?.get(0)?.subcategories?.collection?.let { cAdapter.setList(it) }
        adapter.notifyDataSetChanged()
        cAdapter.notifyDataSetChanged()
    }

    private fun chooseYourInterest(cList: CategoryModel) {

          for(i in cList.collection?.indices!!){
              if(cList.collection[i].label == SharedPreference.getValueString("category")){
                  cList.collection[i].subcategories?.collection?.let { iAdapter.setList(it) }
                  iAdapter.notifyDataSetChanged()
                  }

              }
          }



    override fun onItemCategoryClick(category: CategoryCollection,position : Int) {
        cList.collection?.get(position)?.subcategories?.collection?.let { cAdapter.setList(it) }
        cAdapter.notifyDataSetChanged()
    }


    override fun onItemClick(category: SubCategoryCollection) {
        val i = Intent(this.requireActivity(), DetailsActivity::class.java)
        startActivity(i)
    }

    override fun onTrendingItemClick(category: TrendingModel) {
        val i = Intent(this.requireActivity(), DetailsActivity::class.java)
        startActivity(i)
    }


}