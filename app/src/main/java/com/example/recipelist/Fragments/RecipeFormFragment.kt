package com.example.recipelist.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recipelist.Models.Recipe
import com.example.recipelist.R
import com.example.recipelist.Storage.RecipeViewModel

class RecipeFormFragment : Fragment() {
    private lateinit var viewModel: RecipeViewModel
    private var recipeId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getLong("RECIPE_ID", -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)

        if (recipeId != null && recipeId != -1L) {
            viewModel.getRecipeById(recipeId!!).observe(viewLifecycleOwner) { recipe ->
                if (recipe != null) {
                    view.findViewById<EditText>(R.id.titleEditText).setText(recipe.title)
                    view.findViewById<EditText>(R.id.ingredientsEditText).setText(recipe.ingredients)
                    view.findViewById<EditText>(R.id.instructionsEditText).setText(recipe.instructions)
                }
            }
        }

        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            val title = view.findViewById<EditText>(R.id.titleEditText).text.toString()
            val ingredients = view.findViewById<EditText>(R.id.ingredientsEditText).text.toString()
            val instructions = view.findViewById<EditText>(R.id.instructionsEditText).text.toString()

            if (recipeId != null && recipeId != -1L) {
                val updatedRecipe = Recipe(recipeId!!, title, ingredients, instructions)
                viewModel.updateRecipe(updatedRecipe)
            } else {
                val newRecipe = Recipe(viewModel.getNextId(), title, ingredients, instructions)
                viewModel.addRecipe(newRecipe)
            }
            parentFragmentManager.popBackStack()
        }
    }
}


