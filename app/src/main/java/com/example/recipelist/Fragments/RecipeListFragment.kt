package com.example.recipelist.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipelist.R
import com.example.recipelist.RecipeAdapter
import com.example.recipelist.Storage.RecipeViewModel

class RecipeListFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(RecipeViewModel::class.java)

        adapter = RecipeAdapter { recipe ->
            val detailFragment = RecipeDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("RECIPE_ID", recipe.id)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }

        view.findViewById<Button>(R.id.addRecipeButton).setOnClickListener {
            val recipeFormFragment = RecipeFormFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, recipeFormFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}




