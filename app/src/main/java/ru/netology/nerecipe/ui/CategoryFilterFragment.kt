package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.CategoryFiltersBinding
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
            checkBoxEuropean.text = Category.European.label
            checkBoxAsian.text = Category.Asian.label
            checkBoxPanasian.text = Category.PanAsian.label
            checkBoxEastern.text = Category.Eastern.label
            checkBoxAmerican.text = Category.American.label
            checkBoxRussian.text = Category.Russian.label
            checkBoxMediterranean.text = Category.Mediterranean.label
        }
    }.root


}