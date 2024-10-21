package com.example.recipelist.Models

data class Recipe(
    val id: Long,
    val title: String,
    val ingredients: String,
    val instructions: String
)