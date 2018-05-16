package com.example.subramanyam.recipeapp.Network;

import com.example.subramanyam.recipeapp.data.RecipeItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeJson {


        @GET("baking.json")
        Call<ArrayList<RecipeItem>> getRecipe();

    }

