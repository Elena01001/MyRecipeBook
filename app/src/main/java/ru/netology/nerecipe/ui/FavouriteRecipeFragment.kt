package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.FavouriteFragmentBinding
import ru.netology.nerecipe.viewModel.RecipeViewModel

class FavouriteRecipeFragment : Fragment() {

    private val favouriteRecipeViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FavouriteFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipesAdapter(favouriteRecipeViewModel)
        binding.recipesRecycler.adapter = adapter

        favouriteRecipeViewModel.data.observe(viewLifecycleOwner) { recipes ->

            val favouriteRecipes = recipes.filter { it.addedToFavourites }
            adapter.submitList(favouriteRecipes)

            val emptyList = recipes.none { it.addedToFavourites }
            binding.textEmptyList.visibility =
                if (emptyList) View.GONE else View.VISIBLE
            binding.iconEmptyList.visibility =
                if (emptyList) View.GONE else View.VISIBLE
            }
        }.root

    }


