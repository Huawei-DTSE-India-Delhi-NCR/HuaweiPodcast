package com.huawei.podcast.ui.main.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huawei.podcast.data.model.EpisodeModel
import com.huawei.podcast.data.model.PodCastList
import com.huawei.podcast.data.repository.EpisodeRepository
import com.huawei.podcast.data.repository.MainRepository
import com.huawei.podcast.utils.NetworkHelper
import com.huawei.podcast.utils.Resource
import com.huawei.podcast.utils.SingleLiveEvent
import kotlinx.coroutines.launch


class DetailsViewModel(private val episodeRepository: EpisodeRepository, private val networkHelper: NetworkHelper) : ViewModel() {

    val showError = SingleLiveEvent<String>()
    private val _list = MutableLiveData<Resource<EpisodeModel>>()
    val pList: LiveData<Resource<EpisodeModel>>
        get() = _list

    init {
        fetchPodCastList()
    }

    private fun fetchPodCastList() {
        viewModelScope.launch {
            _list.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                episodeRepository.getEpisode().let {
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