package com.example.subramanyam.recipeapp.userinterface;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.subramanyam.recipeapp.Adapters.RecipeShortDescription;
import com.example.subramanyam.recipeapp.R;
import com.example.subramanyam.recipeapp.data.IngredientItems;
import com.example.subramanyam.recipeapp.data.RecipeItem;
import com.example.subramanyam.recipeapp.widget.UpdateBakingService;

import java.util.ArrayList;
import java.util.List;

import static com.example.subramanyam.recipeapp.userinterface.RecipeDescriptionActivity.SELECTED_RECIPES;


public class RecipeIngrediantDetail extends Fragment {
    ArrayList<RecipeItem> recipe;

    String recipeName;




    public RecipeIngrediantDetail() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        RecyclerView recyclerView;

        TextView textView;



        recipe = new ArrayList<>();





        if(savedInstanceState != null) {

            recipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES);



        }

        else {

            recipe =getArguments().getParcelableArrayList(SELECTED_RECIPES);

        }



        List<IngredientItems> ingredients = recipe.get(0).getIngredients();

        recipeName=recipe.get(0).getName();



        View view = inflater.inflate(R.layout.fragment_recipe_ingrediant_detail, container, false);

        textView = view.findViewById(R.id.detailRecipe);



        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();





        ingredients.forEach((a) ->

        {

            textView.append("\u2022 "+ a.getIngredient()+"\n");

            textView.append("\t\t\t Quantity: "+a.getQuantity().toString()+"\n");

            textView.append("\t\t\t Measure: "+a.getMeasure()+"\n\n");



            recipeIngredientsForWidgets.add(a.getIngredient()+"\n"+

                    "Quantity: "+a.getQuantity().toString()+"\n"+

                    "Measure: "+a.getMeasure()+"\n");

        });



        recyclerView=view.findViewById(R.id.ingrediantsRecipe);

        LinearLayoutManager mLayoutManager=new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(mLayoutManager);



        RecipeShortDescription mRecipeDetailAdapter =new RecipeShortDescription((RecipeDescriptionActivity)getActivity());

        recyclerView.setAdapter(mRecipeDetailAdapter);

        mRecipeDetailAdapter.RecipeIngrediantsData(recipe,getContext());



        //update widget

        UpdateBakingService.startBakingService(getContext(),recipeIngredientsForWidgets);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    @Override

    public void onSaveInstanceState(Bundle currentState) {

        super.onSaveInstanceState(currentState);

        currentState.putParcelableArrayList(SELECTED_RECIPES, recipe);

        currentState.putString("Title",recipeName);

    }
}
