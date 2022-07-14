package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.CategoryFiltersBinding
import ru.netology.nerecipe.databinding.NewRecipeFragmentBinding
import ru.netology.nerecipe.dto.Category
import ru.netology.nerecipe.viewModel.RecipeViewModel

class CategoryFilterFragment : Fragment() {

    private val categoryFilterViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = CategoryFiltersBinding.inflate(layoutInflater, container, false).also { binding ->


        with(binding) {
            checkBoxEuropean.text = Category.European.toString()
            checkBoxAsian.text = Category.Asian.toString()
            checkBoxPanasian.text = Category.PanAsian.toString()
            checkBoxEastern.text = Category.Eastern.toString()
            checkBoxAmerican.text = Category.American.toString()
            checkBoxRussian.text = Category.Russian.toString()
            checkBoxMediterranean.text = Category.Mediterranean.toString()

            binding.ok.setOnClickListener {
                onOkButtonClicked(binding)
            }
        }
    }.root

    private fun onOkButtonClicked(binding: CategoryFiltersBinding) {
        var checkedCount = 0
        val numberOfFilters = 7

        if (!binding.checkBoxEuropean.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.European)
        }
        if (!binding.checkBoxAsian.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.Asian)
        }
        if (!binding.checkBoxPanasian.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.PanAsian)
        }
        if (!binding.checkBoxEastern.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.Eastern)
        }
        if (!binding.checkBoxAmerican.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.American)
        }
        if (!binding.checkBoxRussian.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.Russian)
        }
        if (!binding.checkBoxMediterranean.isChecked) {
            ++checkedCount
            categoryFilterViewModel.showRecipesByCategories(Category.Mediterranean)
        }

        findNavController().popBackStack()
    }

    // чтобы передавать данные между фрагментами
    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "newContent"
    }

}