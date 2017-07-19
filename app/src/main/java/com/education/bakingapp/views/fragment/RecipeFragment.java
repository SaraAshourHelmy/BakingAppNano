package com.education.bakingapp.views.fragment;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.bakingapp.R;
import com.education.bakingapp.models.Recipe;
import com.education.bakingapp.network.utility.NetworkAPIs;
import com.education.bakingapp.network.utility.VolleyResponse;
import com.education.bakingapp.test.SimpleIdlingResource;
import com.education.bakingapp.utilities.Constants;
import com.education.bakingapp.utilities.DialogUtility;
import com.education.bakingapp.views.activity.RecipeDetailsActivity;
import com.education.bakingapp.views.adapter.RecipeAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements RecipeAdapter.OnItemClickListener {

    @Nullable
    public static SimpleIdlingResource idlingResource;
    private RecyclerView recyclerRecipes;
    public static final String EXTRA_RECIPE = "recipe";
    public static final String SAVE_RECIPE_POSITION = "position";
    ArrayList<Recipe> recipes;
    GridLayoutManager layoutManager;
    Parcelable state;
    boolean isLand, isTablet;


    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerRecipes = (RecyclerView) view.findViewById(R.id.recycler_recipes);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        getRecipes();

    }

    private void setIdlingResource(boolean flag) {
        if (idlingResource != null)
            idlingResource.setIdleState(flag);
    }

    private void getRecipes() {
        recipes = new ArrayList<>();
        DialogUtility.showProgressDialog(getContext());
        setIdlingResource(false);
        NetworkAPIs.getRecipes(getContext(), Constants.RECIPES_URL, new VolleyResponse() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onResponseArray(ArrayList<Recipe> lstRecipes) {

                setIdlingResource(false);
                DialogUtility.dismissProgressDialog();
                recipes = lstRecipes;
                setupRecipeData(lstRecipes);

            }

            @Override
            public void onError(String errorMsg) {

                setIdlingResource(false);
                DialogUtility.dismissProgressDialog();
                DialogUtility.showMessageDialog(getContext(), errorMsg);
            }
        });
    }

    private void setupRecipeData(ArrayList<Recipe> lstRecipes) {
        RecipeAdapter adapter = new RecipeAdapter(lstRecipes, this);
        isLand = getResources().getBoolean(R.bool.isLand);

        if (isTablet)
            layoutManager = new GridLayoutManager(getContext(), 3);
        else if (isLand)
            layoutManager = new GridLayoutManager(getContext(), 2);
        else
            layoutManager = new GridLayoutManager(getContext(), 1);
        if (state != null)
            layoutManager.onRestoreInstanceState(state);
        recyclerRecipes.setLayoutManager(layoutManager);
        recyclerRecipes.setAdapter(adapter);

    }

    @Override
    public void onItemClick(Recipe recipe) {

        // create intent to details
        Intent intent = new Intent(getContext(), RecipeDetailsActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        startActivity(intent);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (layoutManager != null && !isTablet) {
            state = layoutManager.onSaveInstanceState();
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setupRecipeData(recipes);
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

                setupRecipeData(recipes);
            }
        }
    }


}
