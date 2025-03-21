package com.example.recipes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

     TextView tvMealName, tvMealCategory, tvMealInstructions;
     Button btnGetRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMealName = findViewById(R.id.tvMealName);
        tvMealCategory = findViewById(R.id.tvMealCategory);
        tvMealInstructions = findViewById(R.id.tvMealInstructions);
        btnGetRecipe = findViewById(R.id.btnGetRecipe);

        btnGetRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomRecipe();
            }
        });
    }

    private void getRandomRecipe() {
        MealApi api = RetrofitClient.getApi();
        Call<MealResponse> call = api.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null) {
                    Meal meal = response.body().getMeals().get(0);
                    tvMealName.setText(meal.getStrMeal());
                    tvMealCategory.setText(meal.getStrCategory());
                    tvMealInstructions.setText(meal.getStrInstructions());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                tvMealName.setText("Error: " + t.getMessage());
            }
        });
    }
}