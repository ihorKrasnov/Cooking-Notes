package com.example.recipelist.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.recipelist.R
import com.example.recipelist.Storage.RecipeViewModel

class RecipeDetailFragment : Fragment() {
    private lateinit var viewModel: RecipeViewModel
    private var recipeId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getLong("RECIPE_ID")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)

        viewModel.getRecipeById(recipeId).observe(viewLifecycleOwner) { recipe ->
            if (recipe != null) {
                view.findViewById<TextView>(R.id.titleTextView).text = recipe.title
                view.findViewById<TextView>(R.id.ingredientsTextView).text = recipe.ingredients
                view.findViewById<TextView>(R.id.instructionsTextView).text = recipe.instructions

                view.findViewById<Button>(R.id.editButton).setOnClickListener {
                    val editFragment = RecipeFormFragment().apply {
                        arguments = Bundle().apply {
                            putLong("RECIPE_ID", recipe.id)
                        }
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, editFragment)
                        .addToBackStack(null)
                        .commit()
                }

                view.findViewById<Button>(R.id.deleteButton).setOnClickListener {
                    viewModel.deleteRecipe(recipe.id)
                    parentFragmentManager.popBackStack()
                }
            } else {
                Toast.makeText(context, "Рецепт не знайдено", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


