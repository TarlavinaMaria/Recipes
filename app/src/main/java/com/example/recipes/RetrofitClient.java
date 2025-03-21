package com.example.recipes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Класс для создания экземпляра Retrofit, который будет использоваться для отправки HTTP-запросов
public class RetrofitClient {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static Retrofit retrofit = null;

    public static MealApi getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MealApi.class);
    }
}
