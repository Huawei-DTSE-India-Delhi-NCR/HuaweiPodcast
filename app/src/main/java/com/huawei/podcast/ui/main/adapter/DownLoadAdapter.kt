package com.huawei.podcast.ui.main.adapter

import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.huawei.podcast.R
import kotlinx.android.synthetic.main.item_downloads.view.*
import java.io.File

class DownLoadAdapter(private val context: Context, private val chaptersList: ArrayList<String>) :
        RecyclerView.Adapter<DownLoadAdapter.ViewHolder>() {
    private lateinit var strReplace : String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_downloads, parent, false))
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        strReplace =  chaptersList[position].replace("/storage/emulated/0/Download/PodCast/","")
        holder.txtTitle.text = strReplace
        holder.imgDownload.setOnClickListener {
            strReplace =  chaptersList[position].replace("/storage/emulated/0/Download/PodCast/","")
            val dir: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/PodCast")
            if(dir.isDirectory && dir.exists()) {
                val file = File(dir, strReplace)
                val deleted: Boolean = file.delete()
                if(deleted) {
                    Toast.makeText(context, "File Deleted Successfully", Toast.LENGTH_LONG).show()
                    chaptersList.removeAt(position)
                    notifyDataSetChanged()
                }else{
                    Toast.makeText(context, "Could Not Delete File", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.txt_title
        val imgDownload: ImageView = view.img_download
    }
}