package com.education.bakingapp.views.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.education.bakingapp.R;
import com.education.bakingapp.models.Recipe;
import com.education.bakingapp.utilities.FragmentUtility;
import com.education.bakingapp.views.adapter.RecipeDetailsAdapter;
import com.education.bakingapp.views.fragment.DetailsRecipeFragment;
import com.education.bakingapp.views.fragment.IngredientFragment;
import com.education.bakingapp.views.fragment.RecipeFragment;
import com.education.bakingapp.views.fragment.StepFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeDetailsActivity extends AppCompatActivity implements
        RecipeDetailsAdapter.OnItemClickListener {

    boolean isTablet;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        setToolbarName();
        bindViews();

    }

    private void bindViews() {
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            setIngredients();
        }
        DetailsRecipeFragment fragment = new DetailsRecipeFragment();
        fragment.setOnItemClickListener(this);
        FragmentUtility.insertFragment(this, R.id.recipeDetails_container, fragment);
    }

    private void setToolbarName() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RecipeFragment.EXTRA_RECIPE)) {
            recipe = intent.getParcelableExtra(RecipeFragment.EXTRA_RECIPE);
            String recipeName = recipe.getName();
            getSupportActionBar().setTitle(recipeName);
        }
    }

    private void setIngredients() {
        IngredientFragment fragment = new IngredientFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList
                (DetailsRecipeFragment.EXTRA_INGREDIENT, new ArrayList<>
                        (Arrays.asList(recipe.getIngredients())));
        fragment.setArguments(bundle);
        FragmentUtility.replaceFragment
                (this, R.id.ingredientStep_container, fragment);
    }

    private void setStep(int position) {
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsRecipeFragment.EXTRA_STEP, recipe.getSteps()[position - 1]);
        fragment.setArguments(bundle);
        FragmentUtility.replaceFragment
                (this, R.id.ingredientStep_container, fragment);

    }

    @Override
    public void onItemClick(int position) {

        switch (position) {
            case 0:
                setIngredients();
                break;
            default:
                setStep(position);
                break;

        }
    }


}
