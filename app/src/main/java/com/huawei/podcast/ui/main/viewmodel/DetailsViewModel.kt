package com.huawei.podcast.ui.main.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huawei.podcast.data.model.PodCastList
import com.huawei.podcast.data.repository.MainRepository
import com.huawei.podcast.utils.NetworkHelper
import com.huawei.podcast.utils.Resource
import com.huawei.podcast.utils.SingleLiveEvent
import kotlinx.coroutines.launch


class DetailsViewModel(private val mainRepository: MainRepository, private val networkHelper: NetworkHelper) : ViewModel() {

    val showError = SingleLiveEvent<String>()
    private val _list = MutableLiveData<Resource<List<PodCastList>>>()
    val pList: LiveData<Resource<List<PodCastList>>>
        get() = _list

    init {
        fetchPodCastList()
    }

    private fun fetchPodCastList() {
        viewModelScope.launch {
            _list.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getPodCastList().let {
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