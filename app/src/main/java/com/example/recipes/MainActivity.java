package com.example.recipes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvMealName, tvMealCategory, tvMealInstructions;
    ImageView ivMealThumb;
    Button btnGetRecipe, btnSearch;
    EditText etIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация компонентов
        tvMealName = findViewById(R.id.tvMealName);
        tvMealCategory = findViewById(R.id.tvMealCategory);
        tvMealInstructions = findViewById(R.id.tvMealInstructions);
        ivMealThumb = findViewById(R.id.ivMealThumb);
        btnGetRecipe = findViewById(R.id.btnGetRecipe);
        btnSearch = findViewById(R.id.btnSearch);
        etIngredient = findViewById(R.id.etIngredient);
        // Случайный рецепт
        btnGetRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomRecipe();
            }
        });
        // Поиск рецептов по ингредиенту
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient = etIngredient.getText().toString().trim();
                if (!ingredient.isEmpty()) {
                    searchMealsByIngredient(ingredient);
                } else {
                    Toast.makeText(MainActivity.this, "Пожалуйста, введите ингредиент", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getRandomRecipe() {
        MealApi api = RetrofitClient.getApi();
        Call<MealResponse> call = api.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                // Обработка успешного ответа, вывод найденного рецепта
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) {
                    Meal meal = response.body().getMeals().get(0);
                    displayMeal(meal);
                }
            }

            // Обработка ошибки
            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                tvMealName.setText("Ошибка: " + t.getMessage());
            }
        });
    }

    private void searchMealsByIngredient(String ingredient) {
        MealApi api = RetrofitClient.getApi();
        Call<MealsByIngredientResponse> call = api.getMealsByIngredient(ingredient);// Вызываем метод getMealsByIngredient, передавая ингредиент
        call.enqueue(new Callback<MealsByIngredientResponse>() {
            @Override
            public void onResponse(Call<MealsByIngredientResponse> call, Response<MealsByIngredientResponse> response) {
                // Обработка успешного ответа
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) { // Проверяем, что ответ успешен и содержит список рецептов
                    List<MealShort> meals = response.body().getMeals(); // Получаем список рецептов
                    if (!meals.isEmpty()) {
                        // Отображаем первый рецепт из списка
                        MealShort mealShort = meals.get(0);
                        getMealDetailsById(mealShort.getIdMeal()); // Вызываем метод getMealDetailsById для получения полного рецепта
                    } else {
                        Toast.makeText(MainActivity.this, "Нет рецептов по ингредиенту", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            // Обработка ошибки
            @Override
            public void onFailure(Call<MealsByIngredientResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMealDetailsById(String mealId) {
        MealApi api = RetrofitClient.getApi();
        Call<MealResponse> call = api.getMealById(mealId); // Вызываем метод getMealById, передавая ID рецепта
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                // Обработка успешного ответа
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) { // Проверяем, что ответ успешен и содержит список рецептов
                    Meal meal = response.body().getMeals().get(0); // Получает первый рецепт из списка
                    displayMeal(meal); // Отображает рецепт на экран
                }
            }

            // Обработка ошибки
            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Отображение рецепта
    private void displayMeal(Meal meal) {
        tvMealName.setText(meal.getStrMeal());
        tvMealCategory.setText(meal.getStrCategory());
        tvMealInstructions.setText(meal.getStrInstructions());
        Glide.with(MainActivity.this)
                .load(meal.getStrMealThumb())
                .into(ivMealThumb);
    }
}