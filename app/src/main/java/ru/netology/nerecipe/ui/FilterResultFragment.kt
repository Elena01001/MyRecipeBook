package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.FavouriteFragmentBinding
import ru.netology.nerecipe.dto.Category
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.viewModel.RecipeViewModel

class FilterResultFragment : Fragment() {

    private val filterResultViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FavouriteFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipesAdapter(filterResultViewModel)

        binding.recipesRecycler.adapter = adapter

       // filterResultViewModel.data.observe(viewLifecycleOwner)

        //организация перехода к фрагменту separateRecipeFragment
        filterResultViewModel.separateRecipeViewEvent.observe(viewLifecycleOwner) { recipeCardId ->
            val direction =
                FilterResultFragmentDirections.actionFilterResultFragmentToSeparateRecipeFragment(
                    recipeCardId
                )
            findNavController().navigate(direction)
        }

        //организация перехода к фрагменту NewOrEditedRecipeFragment
        filterResultViewModel.navigateToRecipeContentScreenEvent.observe(viewLifecycleOwner) { recipe ->
            val direction =
                FilterResultFragmentDirections.actionFilterResultFragmentToNewOrEditedRecipeFragment(
                    recipe
                )
            findNavController().navigate(direction)
        }

        // показываем новый экран в нашем приложении
        // данная ф-ция будет вызвана при завершении CategoryFilterFragment
        setFragmentResultListener(
            requestKey = CategoryFilterFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != CategoryFilterFragment.REQUEST_KEY) return@setFragmentResultListener
            val category = bundle.getParcelable<Category>(
                CategoryFilterFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            filterResultViewModel.showRecipesByCategories(category)
        }
    }.root
}