package com.example.getuplord

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhotoListAdapter internal constructor(
    val context: Context,
    val photos: List<Photo>,
    val clickListener: (String) -> Unit
) : RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

    override fun getItemCount(): Int = photos.size

    //add selected item?

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoItemView: TextView = itemView.findViewById(R.id.textView)

        //add isActive shit?

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        val holder = PhotoViewHolder(itemView)

        itemView.setOnClickListener{
            //add selected item stuff??
            clickListener(photos[holder.adapterPosition].type)
        }
        return holder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val current = photos[position]
        holder.photoItemView.text = current.name

    }
}