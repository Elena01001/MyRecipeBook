package ru.netology.nerecipe.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.adapter.RecipeInteractionListener
import ru.netology.nerecipe.data.InMemoryRecipeRepositoryImpl
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.util.SingleLiveEvent
import java.util.*

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {
    private val repository: RecipeRepository = InMemoryRecipeRepositoryImpl

    val data get() = repository.data

    val separateRecipeViewEvent = SingleLiveEvent<Long>()

    // Эта LiveData хранит текст рецепта, который редактируется, или null, если новый текст добавляется пользователем
    val navigateToRecipeContentScreenEvent = SingleLiveEvent<Recipe?>()
    val filteredRecipes: MediatorLiveData<List<Recipe>> = MediatorLiveData()
    val currentRecipe = MutableLiveData<Recipe?>(null)
    var favoriteFilter: MutableLiveData<Boolean> = MutableLiveData()
    val categoriesList: MutableLiveData<List<String>> = MutableLiveData()
    val searchQuery: MutableLiveData<String> = MutableLiveData()

    init {
        favoriteFilter.value = false

        filteredRecipes.addSource(data) {
            multiFilter()
        }
        filteredRecipes.addSource(searchQuery) {
            multiFilter()
        }
        filteredRecipes.addSource(categoriesList) {
            multiFilter()
        }
        filteredRecipes.addSource(favoriteFilter) {
            multiFilter()
        }
    }

    fun onSaveButtonClicked(recipe: Recipe) { // нужно научить, когда пришел новый рец, а когда неновый для редактирования
        if (recipe.content.isBlank() && recipe.name.isBlank() && recipe.category.isBlank()) return
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

    private fun multiFilter() {
        val searchText = searchQuery.value?.lowercase(Locale.getDefault())?.trim { it <= ' ' } ?: ""
        if (searchText.isNotEmpty() || data.value != null || categoriesList.value != null) {
            val list = data.value?.filter { recipe ->
                recipe.name.lowercase(Locale.getDefault()).contains(searchText)
            }?.filter {
                it.categories.forEach { categoryName ->
                    if (categoriesList.value!!.contains(categoryName)) return@filter true
                }
                return@filter false
            }
            val listFavorite = if (favoriteFilter.value==true) list?.filter{recipe->
                recipe.addedToFavourites
            } else list
            filteredRecipes.value = listFavorite
        } else {
            filteredRecipes.value = data.value
        }
    }
}