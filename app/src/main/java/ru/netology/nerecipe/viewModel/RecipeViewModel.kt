package ru.netology.nerecipe.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.R
import ru.netology.nerecipe.adapter.RecipeInteractionListener
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.data.InMemoryRecipeRepositoryImpl
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.data.RoomRecipeRepositoryImpl
import ru.netology.nerecipe.databinding.CategoryFiltersBinding
import ru.netology.nerecipe.databinding.NewRecipeFragmentBinding
import ru.netology.nerecipe.databinding.RecipeBinding
import ru.netology.nerecipe.db.AppDb
import ru.netology.nerecipe.dto.Category
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.util.SingleLiveEvent
import java.util.*
import kotlin.collections.ArrayList

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {

    //private val repository: RecipeRepository = InMemoryRecipeRepositoryImpl
    private val repository: RecipeRepository = RoomRecipeRepositoryImpl(
        dao = AppDb.getInstance(context = application).recipeDao
    )

    val data get() = repository.data

    val separateRecipeViewEvent = SingleLiveEvent<Long>()

    // Эта LiveData хранит текст рецепта, который редактируется, или null, если новый текст добавляется пользователем
    val navigateToRecipeContentScreenEvent = SingleLiveEvent<Recipe?>()
    val currentRecipe = MutableLiveData<Recipe?>(null)
    var favoriteFilter: MutableLiveData<Boolean> = MutableLiveData()

    init {
        favoriteFilter.value = false
    }

    fun onSaveButtonClicked(recipe: Recipe) { // нужно научить, когда пришел новый рец, а когда неновый для редактирования
        if (recipe.content.isBlank() && recipe.name.isBlank()) return
        val newRecipe = currentRecipe.value?.copy( // создание копии рец с новым содержимым
            content = recipe.content,
            name = recipe.name,
            category = recipe.category
        ) ?: Recipe(
            id = RecipeRepository.NEW_RECIPE_ID,
            author = "Автор: Елена Смелкова",
            name = recipe.name,
            category = recipe.category,
            content = recipe.content
        )
        repository.save(newRecipe)
        currentRecipe.value = null // сброс контента сохраненного рец в строке, где мы его печатали
    }

    fun onAddButtonClicked() {
        navigateToRecipeContentScreenEvent.call()
    }

    fun searchRecipeByName(recipeName: String) = repository.search(recipeName)
    fun clearFilter() {
        repository.getAllRecipes()
    }

    override fun onRemoveButtonClicked(recipe: Recipe) = repository.delete(recipe.id)

    override fun onEditButtonClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeContentScreenEvent.value = recipe
    }

    override fun onRecipeCardClicked(recipe: Recipe) {
        separateRecipeViewEvent.value = recipe.id
    }

    override fun onFavouritesButtonClicked(recipe: Recipe) = repository.addToFavourites(recipe.id)
    override fun onRecipeItemClicked(recipe: Recipe) {
        navigateToRecipeContentScreenEvent.value = recipe
    }

    fun showRecipesByCategories(category: Category) {
        repository.getCategory(category)

    }

    lateinit var binding: CategoryFiltersBinding

    fun onOkButtonClicked(categoryList: ArrayList<Category>): List<Category> {

        if (binding.checkBoxEuropean.isChecked) {
            showRecipesByCategories(Category.European)
            categoryList.add(Category.European)
        }
        if (binding.checkBoxAsian.isChecked) {
            showRecipesByCategories(Category.Asian)
            categoryList.add(Category.Asian)
        }
        if (binding.checkBoxPanasian.isChecked) {
            showRecipesByCategories(Category.PanAsian)
            categoryList.add(Category.PanAsian)
        }
        if (binding.checkBoxEastern.isChecked) {
            showRecipesByCategories(Category.Eastern)
            categoryList.add(Category.Eastern)
        }
        if (binding.checkBoxAmerican.isChecked) {
            showRecipesByCategories(Category.American)
            categoryList.add(Category.American)
        }
        if (binding.checkBoxRussian.isChecked) {
            showRecipesByCategories(Category.Russian)
            categoryList.add(Category.Russian)
        }
        if (binding.checkBoxMediterranean.isChecked) {
            showRecipesByCategories(Category.Mediterranean)
            categoryList.add(Category.Mediterranean)
        }
        return categoryList
    }

}