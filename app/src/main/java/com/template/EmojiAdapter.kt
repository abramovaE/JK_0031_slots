package com.template

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class EmojiAdapter(private val emoji: List<String>,
                   private val context: Context
) : RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder>() {

    class EmojiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.emoji_item, parent, false)
        return EmojiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        holder.textView.text = emoji.get(position)
        val animation = AnimationUtils.loadAnimation(context, R.anim.decr)
        animation.startOffset = (0..500).random().toLong()
        holder.textView.startAnimation(animation)
    }


    override fun getItemCount(): Int {
        return emoji.size
    }
}