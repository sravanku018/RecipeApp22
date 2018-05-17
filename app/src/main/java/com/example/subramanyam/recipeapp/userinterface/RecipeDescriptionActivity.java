package com.example.subramanyam.recipeapp.userinterface;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.subramanyam.recipeapp.Adapters.RecipeShortDescription;
import com.example.subramanyam.recipeapp.R;
import com.example.subramanyam.recipeapp.data.RecipeItem;
import com.example.subramanyam.recipeapp.data.StepsItems;

import java.util.ArrayList;
import java.util.List;

public class RecipeDescriptionActivity extends AppCompatActivity implements RecipeShortDescription.ListItemClickListener,StepDetailsFragmnet.ListItemClickListener{
    static String ALL_RECIPES="All_Recipes";

    static String SELECTED_RECIPES="Selected_Recipes";

    static String SELECTED_STEPS="Selected_Steps";

    static String SELECTED_INDEX="Selected_Index";

    static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";

    static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";





    private ArrayList<RecipeItem> recipe;

    String recipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description);
        if (savedInstanceState == null) {



            Bundle selectedRecipeBundle = getIntent().getExtras();



            recipe = new ArrayList<>();

            recipe = selectedRecipeBundle.getParcelableArrayList(SELECTED_RECIPES);

            recipeName = recipe.get(0).getName();



            final RecipeIngrediantDetail fragment = new RecipeIngrediantDetail();

            fragment.setArguments(selectedRecipeBundle);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()

                    .replace(R.id.fragment_container, fragment).addToBackStack(STACK_RECIPE_DETAIL)

                    .commit();



            if (findViewById(R.id.recipe_linear_layout).getTag()!=null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {



                final StepDetailsFragmnet fragment2 = new StepDetailsFragmnet();

                fragment2.setArguments(selectedRecipeBundle);

                fragmentManager.beginTransaction()

                        .replace(R.id.fragment_container2, fragment2).addToBackStack(STACK_RECIPE_STEP_DETAIL)

                        .commit();



            }







        } else {

            recipeName= savedInstanceState.getString("Title");

        }





        Toolbar myToolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(myToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(recipeName);



        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();

                if (findViewById(R.id.fragment_container2)==null) {

                    if (fm.getBackStackEntryCount() > 1) {

                        //go back to "Recipe Detail" screen

                        fm.popBackStack(STACK_RECIPE_DETAIL, 0);

                    } else if (fm.getBackStackEntryCount() > 0) {

                        //go back to "Recipe" screen

                        finish();

                    }

                }

                else {

                   //go back to "Recipe" screen

                   finish();

               }



            }

        });
    }

    @Override
    public void onListItemClick(List<StepsItems> stepsOut, int clickedItemIndex, String recipeName) {
        final StepDetailsFragmnet fragment = new StepDetailsFragmnet();

        FragmentManager fragmentManager = getSupportFragmentManager();



        getSupportActionBar().setTitle(recipeName);



        Bundle stepBundle = new Bundle();

        stepBundle.putParcelableArrayList(SELECTED_STEPS,(ArrayList<StepsItems>) stepsOut);

        stepBundle.putInt(SELECTED_INDEX,clickedItemIndex);

        stepBundle.putString("Title",recipeName);

        fragment.setArguments(stepBundle);



        if (findViewById(R.id.recipe_linear_layout).getTag()!=null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {

            fragmentManager.beginTransaction()

                    .replace(R.id.fragment_container2, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)

                    .commit();



        }

        else {

            fragmentManager.beginTransaction()

                    .replace(R.id.fragment_container, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)

                    .commit();

        }
    }


    @Override

    public void onSaveInstanceState(Bundle outState) {

            outState.putString("Title",recipeName);

        super.onSaveInstanceState(outState);


    }
}
