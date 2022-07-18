package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.adapter.showCategories
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
            checkBoxEuropean.text = checkBoxEuropean.context.showCategories(Category.European)
            checkBoxAsian.text = checkBoxAsian.context.showCategories(Category.Asian)
            checkBoxPanasian.text = checkBoxPanasian.context.showCategories(Category.PanAsian)
            checkBoxEastern.text = checkBoxEastern.context.showCategories(Category.Eastern)
            checkBoxAmerican.text = checkBoxAmerican.context.showCategories(Category.American)
            checkBoxRussian.text = checkBoxRussian.context.showCategories(Category.Russian)
            checkBoxMediterranean.text =
                checkBoxMediterranean.context.showCategories(Category.Mediterranean)

            binding.ok.setOnClickListener {
                onOkButtonClicked(binding)
            }
        }

    }.root

    fun onOkButtonClicked(binding: CategoryFiltersBinding): List<Category> {

        val categoryList = arrayListOf<Category>()

        if (binding.checkBoxEuropean.isChecked) {
            categoryFilterViewModel.showRecipesByCategories(Category.European)
            categoryList.add(Category.European)
        }
        if (binding.checkBoxAsian.isChecked) {
            categoryFilterViewModel.showRecipesByCategories(Category.Asian)
            categoryList.add(Category.Asian)
        }
        if (binding.checkBoxPanasian.isChecked) {
            categoryFilterViewModel.showRecipesByCategories(Category.PanAsian)
            categoryList.add(Category.PanAsian)
        }
        if (binding.checkBoxEastern.isChecked) {
            categoryFilterViewModel.showRecipesByCategories(Category.Eastern)
            categoryList.add(Category.Eastern)
        }
        if (binding.checkBoxAmerican.isChecked) {
            categoryFilterViewModel.showRecipesByCategories(Category.American)
            categoryList.add(Category.American)
        }
        if (binding.checkBoxRussian.isChecked) {
            categoryFilterViewModel.showRecipesByCategories(Category.Russian)
            categoryList.add(Category.Russian)
        }
        if (binding.checkBoxMediterranean.isChecked) {
            categoryFilterViewModel.showRecipesByCategories(Category.Mediterranean)
            categoryList.add(Category.Mediterranean)
        }
        findNavController().popBackStack()
        return categoryList
    }


    // чтобы передавать данные между фрагментами
    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "newContent"
    }

}