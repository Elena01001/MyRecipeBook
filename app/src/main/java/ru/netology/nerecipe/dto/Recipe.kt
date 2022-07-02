package ru.netology.nerecipe.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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

) : Parcelable {

    @Parcelize
    enum class Category(
        val key: String,
        val isChosen: Boolean = true
    ) : Parcelable {
        European("Европейская",true),
        Asian("Азиатская",true),
        PanAsian("Паназиатская",true),
        Eastern("Восточная",true),
        American("Американская",true),
        Russian("Русская",true),
        Mediterranean("Средиземноморская",true)
    }
}





