package ru.netology.nerecipe.db

import ru.netology.nerecipe.dto.Recipe

fun RecipeEntity.toModel() = Recipe(
    // мы не можем вытащить данные по названию колонки, а только по ее ид(или индексу)
    id = id,
    name = name,
    author = author,
    content = content,
    category = category,
    //categories = categories,
    addedToFavourites = addedToFavourites,
    foodImage = foodImage
)

fun Recipe.toEntity() = RecipeEntity(
    id = id,
    name = name,
    author = author,
    content = content,
    category = category,
   // categories = categories,
    addedToFavourites = addedToFavourites,
    foodImage = foodImage
)

 //TODO не удается также сделать с категориями, пишет, что enum classes cannot be instantiated