package com.education.bakingapp.views.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.education.bakingapp.R;
import com.education.bakingapp.utilities.FragmentUtility;
import com.education.bakingapp.views.fragment.DetailsRecipeFragment;
import com.education.bakingapp.views.fragment.IngredientFragment;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        if (savedInstanceState == null) {
            setToolbarName();
            bindViews();
        }
    }

    private void bindViews() {
        IngredientFragment fragment = new IngredientFragment();
        FragmentUtility.insertFragment(this, R.id.ingredient_container, fragment);
    }

    private void setToolbarName() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(DetailsRecipeFragment.EXTRA_NAME)) {
            String recipeName = intent.getStringExtra(DetailsRecipeFragment.EXTRA_NAME);
            getSupportActionBar().setTitle(recipeName);
        }
    }
}
