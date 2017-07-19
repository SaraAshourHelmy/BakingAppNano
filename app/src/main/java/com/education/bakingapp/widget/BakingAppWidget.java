package com.education.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.education.bakingapp.R;
import com.education.bakingapp.views.activity.IngredientActivity;
import com.education.bakingapp.views.activity.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = getBtnRecipes(context);
        views = getListData(views, context);

        // Instruct the widget manager to update the widget

        appWidgetManager.updateAppWidget(appWidgetId, views);
        //appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lstV_recipes);

    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager
            , int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
       /* for (int appWidgetId : appWidgetIds) {
            //getListData(context);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
        UpdateService.startService(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getBtnRecipes(Context context) {

        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.baking_app_widget);

        // get Recipes
        Intent getRecipeIntent = new Intent(context, UpdateService.class);
        PendingIntent btnPending = PendingIntent.getService(context, 1, getRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.btn_getRecipe, btnPending);
        return views;
    }

    private static RemoteViews getListData(RemoteViews views, Context context) {


        Intent intent = new Intent(context, RecipeService.class);
        views.setRemoteAdapter(R.id.lstV_recipes, intent);

        // pending intent
        Intent recipeIntent = new Intent(context, IngredientActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0
                , recipeIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.lstV_recipes, pendingIntent);
        //views.setEmptyView(R.id.lstV_recipes, R.id.tv_empty);
        return views;
    }
}

