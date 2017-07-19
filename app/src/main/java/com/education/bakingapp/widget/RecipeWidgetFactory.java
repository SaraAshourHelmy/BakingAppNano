package com.education.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.education.bakingapp.R;
import com.education.bakingapp.models.Recipe;
import com.education.bakingapp.network.utility.NetworkAPIs;
import com.education.bakingapp.network.utility.VolleyResponse;
import com.education.bakingapp.utilities.Constants;
import com.education.bakingapp.views.activity.IngredientActivity;
import com.education.bakingapp.views.fragment.DetailsRecipeFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sara on 7/18/2017.
 */

public class RecipeWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    public static ArrayList<Recipe> recipes;

    public RecipeWidgetFactory(Context appContext) {
        mContext = appContext;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (recipes == null) {

        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (recipes == null)
            return 0;
        else
            return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (recipes == null || recipes.size() == 0)
            return null;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.adapter_widget);
        views.setTextViewText(R.id.tv_widgetRecipeName, recipes.get(position).getName());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(DetailsRecipeFragment.EXTRA_NAME,
                recipes.get(position).getName());
        fillInIntent.putParcelableArrayListExtra(
                DetailsRecipeFragment.EXTRA_INGREDIENT,
                new ArrayList<>(Arrays.asList(recipes.get(position).getIngredients())));
        views.setOnClickFillInIntent(R.id.tv_widgetRecipeName, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
