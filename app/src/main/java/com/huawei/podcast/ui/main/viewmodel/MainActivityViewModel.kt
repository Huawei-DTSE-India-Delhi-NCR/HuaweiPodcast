package com.huawei.podcast.ui.main.viewmodel

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.huawei.podcast.ui.main.view.SearchActivity

class MainActivityViewModel : ViewModel() {

     fun onClick(view: View){
         val i = Intent(view.context, SearchActivity::class.java)
         view.context.startActivity(i)
    }
}