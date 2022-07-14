package ru.netology.nerecipe.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.R
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.FeedFragmentBinding
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.viewModel.RecipeViewModel

class FeedFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //организация перехода к фрагменту separateRecipeFragment
        viewModel.separateRecipeViewEvent.observe(this) { recipeCardId ->
            val direction =
                FeedFragmentDirections.actionFeedFragmentToSeparateRecipeFragment(recipeCardId)
            findNavController().navigate(direction)
        }

        //организация перехода к фрагменту NewOrEditedRecipeFragment
        viewModel.navigateToRecipeContentScreenEvent.observe(this) { recipe ->
            val direction = FeedFragmentDirections.actionFeedFragmentToNewRecipeFragment(recipe)
            findNavController().navigate(direction)
        }
    }

    override fun onResume() {
        super.onResume()
        // показываем новый экран в нашем приложении
        // данная ф-ция будет вызвана при завершении NewOrEditedRecipeFragment
        setFragmentResultListener(
            requestKey = NewOrEditedRecipeFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != NewOrEditedRecipeFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipe = bundle.getParcelable<Recipe>(
                NewOrEditedRecipeFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newRecipe)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddButtonClicked()
        }

        val search = binding.search
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.submitList(viewModel.data.value)
                    return true
                }
                var recipeList = adapter.currentList
                recipeList = recipeList.filter { recipe ->
                    recipe.name.lowercase().contains(newText.lowercase())
                }
                viewModel.searchRecipeByName(newText)
                adapter.submitList(recipeList)
                return true
            }

        })

        }.root

   }


