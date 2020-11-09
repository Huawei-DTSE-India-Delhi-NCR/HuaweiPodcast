package com.huawei.podcast.di.module

import com.huawei.podcast.data.repository.CategoryRepository
import com.huawei.podcast.data.repository.EpisodeRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        CategoryRepository(get())
    }
    single {
        EpisodeRepository(get())
    }
}