package com.huawei.podcast.di.module

import com.huawei.podcast.data.repository.CategoryRepository
import com.huawei.podcast.data.repository.EpisodeRepository
import com.huawei.podcast.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        MainRepository(get())
    }
    single {
        CategoryRepository(get())
    }
    single {
        EpisodeRepository(get())
    }
}