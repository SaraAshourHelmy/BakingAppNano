package com.education.bakingapp.views.fragment;


import android.content.Intent;
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
import com.education.bakingapp.models.Ingredient;
import com.education.bakingapp.views.adapter.IngredientAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientFragment extends Fragment {


    private static ArrayList<Ingredient> ingredients;
    private RecyclerView recyclerIngredient;
    GridLayoutManager layoutManager;
    Parcelable state;
    boolean isLand, isTablet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(RecipeFragment.SAVE_RECIPE_POSITION))
            state = savedInstanceState.getParcelable(RecipeFragment.SAVE_RECIPE_POSITION);

        isLand = getResources().getBoolean(R.bool.isLand);
    }

    public IngredientFragment() {
        // Required empty public constructor
    }

    private void receiveData() {
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (!isTablet) {
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(DetailsRecipeFragment.EXTRA_INGREDIENT)) {
                ingredients =
                        intent.getParcelableArrayListExtra(DetailsRecipeFragment.EXTRA_INGREDIENT);
            }
        } else {
            Bundle bundle = getArguments();
            if (bundle != null && bundle.containsKey(DetailsRecipeFragment.EXTRA_INGREDIENT))
                ingredients =
                        bundle.getParcelableArrayList(DetailsRecipeFragment.EXTRA_INGREDIENT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerIngredient = (RecyclerView) view.findViewById(R.id.recycler_ingredient);
        if (state == null)
            receiveData();
        setupIngredient();
    }

    private void setupIngredient() {
        IngredientAdapter adapter = new IngredientAdapter(getContext(), ingredients);

        layoutManager = new GridLayoutManager(getContext(), 1);

        if (state != null)
            layoutManager.onRestoreInstanceState(state);
        recyclerIngredient.setLayoutManager(layoutManager);
        recyclerIngredient.setAdapter(adapter);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (layoutManager != null)
            outState.putParcelable(RecipeFragment.SAVE_RECIPE_POSITION,
                    layoutManager.onSaveInstanceState());

    }
}
