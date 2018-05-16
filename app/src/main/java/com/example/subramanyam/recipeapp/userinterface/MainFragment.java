package com.example.subramanyam.recipeapp.userinterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.subramanyam.recipeapp.Adapters.RecipeName;
import com.example.subramanyam.recipeapp.Idling.SimpleIdlingResource;
import com.example.subramanyam.recipeapp.Network.Client;
import com.example.subramanyam.recipeapp.Network.RecipeJson;
import com.example.subramanyam.recipeapp.R;
import com.example.subramanyam.recipeapp.data.RecipeItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.subramanyam.recipeapp.userinterface.RecipeItemActivity.ALL_RECIPES;


public class MainFragment extends Fragment {

    public MainFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView;
        View view= inflater.inflate(R.layout.fragment_main, container, false);




        recyclerView= view.findViewById(R.id.recipe_recycler);

        RecipeName recipesAdapter =new RecipeName((RecipeItemActivity)getActivity());

        recyclerView.setAdapter(recipesAdapter);


        if (view.getTag()!=null && view.getTag().equals("phone-land")){

            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),4);

            recyclerView.setLayoutManager(mLayoutManager);

        }

        else {

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(mLayoutManager);

        }

        RecipeJson recipeJson = Client.getClient();

        Call<ArrayList<RecipeItem>> recipe = recipeJson.getRecipe();



        SimpleIdlingResource idlingResource = (SimpleIdlingResource)((RecipeItemActivity)getActivity()).getmIdlingResource();



        if (idlingResource != null) {

            idlingResource.setIdleState(false);

        }





        recipe.enqueue(new Callback<ArrayList<RecipeItem>>() {

            @Override

            public void onResponse(Call<ArrayList<RecipeItem>> call, Response<ArrayList<RecipeItem>> response) {

                Integer statusCode = response.code();

                Log.v("status code: ", statusCode.toString());



                ArrayList<RecipeItem> recipes = response.body();



                Bundle recipesBundle = new Bundle();

                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);



                recipesAdapter.setRecipeData(recipes,getContext());

                if (idlingResource != null) {

                    idlingResource.setIdleState(true);

                }



            }



            @Override

            public void onFailure(Call<ArrayList<RecipeItem>> call, Throwable t) {

                Log.v("http fail: ", t.getMessage());

            }

        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event


}
