package com.education.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.education.bakingapp.R;
import com.education.bakingapp.models.Recipe;
import com.education.bakingapp.network.utility.NetworkAPIs;
import com.education.bakingapp.network.utility.VolleyResponse;
import com.education.bakingapp.utilities.Constants;

import java.util.ArrayList;

/**
 * Created by Sara on 7/18/2017.
 */

public class UpdateService extends IntentService {

    public UpdateService() {
        super("UpdateService");
    }

    void getRecipe() {
        NetworkAPIs.getRecipes(this, Constants.RECIPES_URL, new VolleyResponse() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onResponseArray(ArrayList<Recipe> lstRecipes) {

                if (RecipeWidgetFactory.recipes != null)
                    RecipeWidgetFactory.recipes = null;
                RecipeWidgetFactory.recipes = lstRecipes;
                updateWidget();
            }

            @Override
            public void onError(String errorMsg) {

                Log.e("error", errorMsg);

            }
        });
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getRecipe();
    }

    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                BakingAppWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lstV_recipes);
        BakingAppWidget.updateRecipeWidgets
                (this, appWidgetManager, appWidgetIds);
    }

    public static void startService(Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        context.startService(intent);
    }
}
