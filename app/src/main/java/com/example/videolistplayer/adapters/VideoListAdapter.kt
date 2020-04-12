package com.example.videolistplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.videolistplayer.R
import com.example.videolistplayer.databinding.ItemVideoBinding

class VideoListAdapter(
    private val mUrls: List<String>,
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<VideoListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemVideoBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.item_video, parent, false)
        return MyViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
       return mUrls.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemVideoBinding? = DataBindingUtil.bind(itemView)
    }

}