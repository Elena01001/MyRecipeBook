package ru.netology.nerecipe.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") // чтобы не слетели имена при релизной сборке
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "category")
    val category: String = "",
    //@ColumnInfo(name = "categories")
    //val categories: List<String> = emptyList(), // TODO ругается БД на категории, не знает, как и в какую таблицу их сохранять
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "addedToFavourites")
    val addedToFavourites: Boolean,
    @ColumnInfo(name = "foodImage")
    val foodImage: String = ""
)


@Entity(tableName = "categories")
enum class CategoryEntity(
    @ColumnInfo(name = "label")
    val label: String
) {
    @ColumnInfo(name = "European")
    European("Европейская"),

    @ColumnInfo(name = "Asian")
    Asian("Азиатская"),

    @ColumnInfo(name = "PanAsian")
    PanAsian("Паназиатская"),

    @ColumnInfo(name = "Eastern")
    Eastern("Восточная"),

    @ColumnInfo(name = "American")
    American("Американская"),

    @ColumnInfo(name = "Russian")
    Russian("Русская"),

    @ColumnInfo(name = "Mediterranean")
    Mediterranean("Средиземноморская")
}
