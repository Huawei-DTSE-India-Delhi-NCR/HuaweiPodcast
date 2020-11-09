package com.huawei.podcast.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huawei.podcast.data.model.CategoryCollection
import com.huawei.podcast.data.model.CategoryModel
import com.huawei.podcast.data.model.PodCastList
import com.huawei.podcast.data.repository.CategoryRepository
import com.huawei.podcast.data.repository.MainRepository
import com.huawei.podcast.utils.NetworkHelper
import com.huawei.podcast.utils.Resource
import com.huawei.podcast.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(private val catRepository: CategoryRepository, private val networkHelper: NetworkHelper) : ViewModel() {

    val showError = SingleLiveEvent<String>()
    private val _list = MutableLiveData<Resource<CategoryModel>>()
    val pList: LiveData<Resource<CategoryModel>>
        get() = _list

    init {
        fetchPodCastList()
    }

    private fun fetchPodCastList() {
        viewModelScope.launch {
            _list.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                catRepository.getPodCastCategoryList().let {
                    if (it.isSuccessful) {
                        showError.value = null
                        _list.postValue(Resource.success(it.body()))
                    } else {
                        showError.value = it.errorBody().toString()
                        _list.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else _list.postValue(Resource.error("No internet connection", null))


        }
    }

}