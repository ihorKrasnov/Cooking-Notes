package com.example.recipelist.Storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipelist.Models.Recipe

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<Recipe>>(emptyList())
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private var nextId = 1L;

    fun addRecipe(recipe: Recipe) {
        _recipes.value = _recipes.value?.plus(recipe)
        nextId = maxOf(nextId, recipe.id + 1)
    }

    fun updateRecipe(updatedRecipe: Recipe) {
        _recipes.value = _recipes.value?.map {
            if (it.id == updatedRecipe.id) updatedRecipe else it
        }
    }

    fun deleteRecipe(recipeId: Long) {
        _recipes.value = _recipes.value?.filter { it.id != recipeId }
    }

    fun getRecipeById(recipeId: Long): LiveData<Recipe?> {
        val recipe = MutableLiveData<Recipe?>()
        _recipes.value?.let { recipes ->
            recipe.value = recipes.find { it.id == recipeId }
        }
        return recipe
    }

    fun getNextId(): Long {
        return nextId
    }
}

