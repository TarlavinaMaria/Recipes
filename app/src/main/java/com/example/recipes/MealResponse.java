package com.example.recipes;

import java.util.List;

// Класс для преобразования JSON в объект
public class MealResponse {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
