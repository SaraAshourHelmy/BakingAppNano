package com.education.bakingapp.views.fragment;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.bakingapp.R;
import com.education.bakingapp.models.Recipe;
import com.education.bakingapp.views.activity.IngredientActivity;
import com.education.bakingapp.views.activity.StepActivity;
import com.education.bakingapp.views.adapter.RecipeDetailsAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsRecipeFragment extends Fragment implements
        RecipeDetailsAdapter.OnItemClickListener {
    private RecyclerView recyclerRecipesDetails;
    public static Recipe recipe;
    public static final String EXTRA_INGREDIENT = "ingredient";
    public static final String EXTRA_STEP = "step";
    public static final String EXTRA_NAME = "recipeName";
    public static final String EXTRA_POSITION = "position";
    GridLayoutManager layoutManager;
    Parcelable state;
    boolean isLand, isTablet;
    RecipeDetailsAdapter.OnItemClickListener onItemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isLand = getResources().getBoolean(R.bool.isLand);
        isTablet = getResources().getBoolean(R.bool.isTablet);
    }

    private void receiveData() {
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(RecipeFragment.EXTRA_RECIPE)) {
            recipe = intent.getParcelableExtra(RecipeFragment.EXTRA_RECIPE);
        }
    }

    public DetailsRecipeFragment() {
        // Required empty public constructor
    }

    public void setOnItemClickListener(RecipeDetailsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_recipe, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerRecipesDetails = (RecyclerView) view.findViewById(R.id.recycler_recipeDetails);
        receiveData();
        if (recipe != null)
            setupRecipeData();
    }
    private void setupRecipeData() {
        RecipeDetailsAdapter adapter = new RecipeDetailsAdapter(getContext(),
                recipe.getSteps(), this);
        if (isLand && !isTablet)
            layoutManager = new GridLayoutManager(getContext(), 2);
        else
            layoutManager = new GridLayoutManager(getContext(), 1);
        if (state != null)
            layoutManager.onRestoreInstanceState(state);
        recyclerRecipesDetails.setLayoutManager(layoutManager);
        recyclerRecipesDetails.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {

        if (!isTablet) {
            switch (position) {
                case 0:
                    // move to ingredient
                    moveToIngredients();
                    break;
                default:
                    // move to step
                    moveToStep(position);
                    break;
            }
        } else {
            onItemClickListener.onItemClick(position);
        }
    }

    private void moveToIngredients() {
        Intent intent = new Intent(getContext(), IngredientActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_INGREDIENT,
                new ArrayList<>(Arrays.asList(recipe.getIngredients())));
        intent.putExtra(EXTRA_NAME, recipe.getName());
        startActivity(intent);
    }

    private void moveToStep(int position) {
        Intent intent = new Intent(getContext(), StepActivity.class);
        intent.putExtra(EXTRA_STEP, recipe.getSteps()[position - 1]);
        intent.putExtra(EXTRA_NAME, recipe.getName());
        intent.putExtra(EXTRA_POSITION, position - 1);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (layoutManager != null && !isTablet) {
            state = layoutManager.onSaveInstanceState();
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                isLand = true;
                setupRecipeData();
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                isLand = false;
                setupRecipeData();
            }
        }

    }


}
