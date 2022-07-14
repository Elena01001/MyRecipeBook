package ru.netology.nerecipe.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.netology.nerecipe.R

@Serializable
@Parcelize
data class Recipe(
    val id: Long,
    val name: String,
    val author: String = "",
    val category: Category,
    val content: String,
    val addedToFavourites: Boolean = false,
    val foodImage: String = ""

) : Parcelable

@Serializable
@Parcelize
enum class Category(val label: String): Parcelable {
    European(R.string.european_type.toString()),
    Asian(R.string.asian_type.toString()),
    PanAsian(R.string.panasian_type.toString()),
    Eastern(R.string.eastern_type.toString()),
    American(R.string.american_type.toString()),
    Russian(R.string.russian_type.toString()),
    Mediterranean(R.string.mediterranean_type.toString())
}






