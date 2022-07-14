package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nerecipe.R
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.databinding.NewRecipeFragmentBinding
import ru.netology.nerecipe.dto.Category
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.viewModel.RecipeViewModel

class NewOrEditedRecipeFragment : Fragment() {

    private val args by navArgs<NewOrEditedRecipeFragmentArgs>()

    private val newOrEditedRecipeViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = NewRecipeFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val thisRecipe = args.currentRecipe
        if (thisRecipe != null) {
            with(binding) {
                name.setText(thisRecipe.name)
                content.setText(thisRecipe.content)
                categoryRecipeCheckBox.check(R.id.checkBoxEuropean)
                categoryRecipeCheckBox.check(R.id.checkBoxAsian)
                categoryRecipeCheckBox.check(R.id.checkBoxPanasian)
                categoryRecipeCheckBox.check(R.id.checkBoxEastern)
                categoryRecipeCheckBox.check(R.id.checkBoxAmerican)
                categoryRecipeCheckBox.check(R.id.checkBoxRussian)
                categoryRecipeCheckBox.check(R.id.checkBoxMediterranean)
                checkBoxEuropean.text = Category.European.toString()
                checkBoxAsian.text = Category.Asian.toString()
                checkBoxPanasian.text = Category.PanAsian.toString()
                checkBoxEastern.text = Category.Eastern.toString()
                checkBoxAmerican.text = Category.American.toString()
                checkBoxRussian.text = Category.Russian.toString()
                checkBoxMediterranean.text = Category.Mediterranean.toString()
            }
        }

        binding.name.requestFocus()

        binding.categoryRecipeCheckBox.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.checkBoxEuropean -> Category.European.toString()
                R.id.checkBoxAsian -> Category.Asian.toString()
                R.id.checkBoxPanasian -> Category.PanAsian.toString()
                R.id.checkBoxEastern -> Category.Eastern.toString()
                R.id.checkBoxAmerican -> Category.American.toString()
                R.id.checkBoxRussian -> Category.Russian.toString()
                R.id.checkBoxMediterranean -> Category.Mediterranean.toString()
            }
        }
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }

    }.root

    private fun onOkButtonClicked(binding: NewRecipeFragmentBinding) {
        val currentRecipe = Recipe(
            id = args.currentRecipe?.id ?: RecipeRepository.NEW_RECIPE_ID,
            name = binding.name.text.toString(),
            content = binding.content.text.toString(),
            category = Category.valueOf(binding.categoryRecipeCheckBox.toString())
        )
        if (emptyFieldsCheck(recipe = currentRecipe)) {
            val resultBundle = Bundle(1)
            resultBundle.putParcelable(RESULT_KEY, currentRecipe)
            setFragmentResult(REQUEST_KEY, resultBundle)
        }
        findNavController().popBackStack()
    }


    private fun emptyFieldsCheck(recipe: Recipe): Boolean {
        return if (recipe.name.isBlank() && recipe.content.isBlank()) {
            Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_LONG).show()
            false
        } else true
    }

    // чтобы передавать данные между фрагментами
    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "newContent"
    }
}

