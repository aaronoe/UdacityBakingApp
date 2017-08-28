package de.aaronoe.baking.storage;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import javax.inject.Inject;

import de.aaronoe.baking.BakingApp;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.widget.RecipeWidgetProvider;

public class RecipeInfoManager {

    private static final String PREF_NAME = "recipe_prefs";
    private static final String RECIPE_KEY = "recipe_key";

    private TypeAdapter<Recipe> mRecipeAdapter;
    private SharedPreferences mSharedPrefs;
    private BakingApp mApplication;

    @Inject
    Gson mGson;

    public RecipeInfoManager(BakingApp app) {
        app.getNetComponent().inject(this);
        mApplication = app;
        mRecipeAdapter = mGson.getAdapter(Recipe.class);
        mSharedPrefs = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public Recipe getRecipe() {
        String recipe = mSharedPrefs.getString(RECIPE_KEY, null);
        try {
            return mRecipeAdapter.fromJson(recipe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void updateAppWidget() {
        Intent intent = new Intent(mApplication, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mApplication);
        ComponentName componentName = new ComponentName(mApplication, RecipeWidgetProvider.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetManager.getAppWidgetIds(componentName));

        mApplication.sendBroadcast(intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(componentName), R.id.widget_ingredient_list);
    }



    public void saveRecipe(Recipe recipe) {
        mSharedPrefs.edit().putString(RECIPE_KEY, mRecipeAdapter.toJson(recipe)).apply();

        updateAppWidget();
    }

}
