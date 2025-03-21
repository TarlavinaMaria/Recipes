package com.example.recipes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// Интерфейс API для работы с рецептами
public interface MealApi {
    // Получение случайного рецепта
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    // Получение рецептов по ингредиенту
    @GET("filter.php")
    Call<MealsByIngredientResponse> getMealsByIngredient(@Query("i") String ingredient);

    // Получение рецепта по его ID, для отображения полного рецепта
    @GET("lookup.php")
    Call<MealResponse> getMealById(@Query("i") String mealId);
}
