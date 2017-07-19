package com.education.bakingapp.views.activity;

import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.education.bakingapp.R;
import com.education.bakingapp.test.SimpleIdlingResource;
import com.education.bakingapp.utilities.FragmentUtility;
import com.education.bakingapp.views.fragment.RecipeFragment;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        //if (savedInstanceState == null)
        bindViews();
    }

    private void bindViews() {

        RecipeFragment fragment = new RecipeFragment();
        FragmentUtility.insertFragment(this, R.id.recipe_container, fragment);
    }

    public IdlingResource getIdlingResource() {
        if (RecipeFragment.idlingResource == null) {
            RecipeFragment.idlingResource = new SimpleIdlingResource();
        }
        return RecipeFragment.idlingResource;
    }
}
