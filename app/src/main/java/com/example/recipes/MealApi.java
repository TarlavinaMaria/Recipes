package com.example.recipes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealApi {
    @GET("random.php")
    Call<MealResponse> getRandomMeal();
}
