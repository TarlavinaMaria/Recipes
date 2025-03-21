package com.example.recipes;

import java.util.List;

// Класс для преобразования JSON в объект, для поиска рецептов по ингредиенту
public class MealsByIngredientResponse {
    private List<MealShort> meals;

    public List<MealShort> getMeals() {
        return meals;
    }

    public void setMeals(List<MealShort> meals) {
        this.meals = meals;
    }
}
