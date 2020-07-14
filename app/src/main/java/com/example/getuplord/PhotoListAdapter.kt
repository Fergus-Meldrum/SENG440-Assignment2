package com.example.getuplord

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * adapter for list (recycler-view) of clothing items: creates and sets content for each option in list
 */
class PhotoListAdapter internal constructor(
    val context: Context,
    val photos: List<Photo>,
    val clickListener: (String) -> Unit
) : RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

    /**
     * required adapter function
     */
    override fun getItemCount(): Int = photos.size

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoItemView: TextView = itemView.findViewById(R.id.textView)
    }

    /**
     * required adapter function
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        val holder = PhotoViewHolder(itemView)

        itemView.setOnClickListener{
            //gives the click listener back the file location of the clothing item's photo
            clickListener(photos[holder.adapterPosition].location)
        }
        return holder
    }

    /**
     * required adapter function
     */
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val current = photos[position]
        holder.photoItemView.text = current.name

    }
}