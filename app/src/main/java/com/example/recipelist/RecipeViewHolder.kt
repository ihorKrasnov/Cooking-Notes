package com.example.recipelist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipelist.Models.Recipe

class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

    fun bind(recipe: Recipe, onClick: (Recipe) -> Unit) {
        titleTextView.text = recipe.title
        itemView.setOnClickListener { onClick(recipe) }
    }
}
