package ru.netology.nerecipe.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    val id: Long,
    val name: String,
    val author: String = "",
    val category: String,
    val categories: List<String> = emptyList(),
    val content: String,
    val addedToFavourites: Boolean = false,
    val foodImage: String = ""

) : Parcelable

@Serializable
@Parcelize
enum class Category(
    val label: String
) : Parcelable {
    European("Европейская"),
    Asian("Азиатская"),
    PanAsian("Паназиатская"),
    Eastern("Восточная"),
    American("Американская"),
    Russian("Русская"),
    Mediterranean("Средиземноморская")
}






