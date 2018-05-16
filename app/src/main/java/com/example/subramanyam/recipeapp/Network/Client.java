package com.example.subramanyam.recipeapp.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
 static RecipeJson recipeJson;

    public static RecipeJson getClient()

    {

        Gson gson = new GsonBuilder().create();



        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

            String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
           recipeJson= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .callFactory(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(RecipeJson.class);



return recipeJson;
    }


}
