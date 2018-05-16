package com.example.subramanyam.recipeapp.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.subramanyam.recipeapp.Adapters.RecipeName;
import com.example.subramanyam.recipeapp.Idling.SimpleIdlingResource;
import com.example.subramanyam.recipeapp.R;
import com.example.subramanyam.recipeapp.data.RecipeItem;

import java.util.ArrayList;

public class RecipeItemActivity extends AppCompatActivity implements RecipeName.ListItemClickListener{
    static String ALL_RECIPES = "All_Recipes";

    static String SELECTED_RECIPES = "Selected_Recipes";

    static String SELECTED_STEPS = "Selected_Steps";

    static String SELECTED_INDEX = "Selected_Index";


    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @Nullable
    public IdlingResource getmIdlingResource() {
        if (mIdlingResource == null) {

            mIdlingResource = new SimpleIdlingResource();

        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar myToolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(myToolbar);

        getSupportActionBar().setHomeButtonEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setTitle("Recipe");


        getmIdlingResource();
    }


    @Override
    public void onListItemClick(RecipeItem selectedItemIndex) {



        Bundle selectedRecipeBundle = new Bundle();

        ArrayList<RecipeItem> selectedRecipe = new ArrayList<>();

        selectedRecipe.add(selectedItemIndex);

        selectedRecipeBundle.putParcelableArrayList(SELECTED_RECIPES,selectedRecipe);



        final Intent intent = new Intent(this,RecipeDescriptionActivity.class);

        intent.putExtras(selectedRecipeBundle);

        startActivity(intent);



    }





    @Override

    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

    }


}
