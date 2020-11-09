package com.huawei.podcast.di.module

import com.huawei.podcast.ui.main.viewmodel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ChooseInterestViewModel(get(), get())
    }
    viewModel {
        MainActivityViewModel()
    }
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        DetailsViewModel(get(),get())
    }
    viewModel {
        EpisodeDetailsViewModel()
    }
}