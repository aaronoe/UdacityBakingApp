package de.aaronoe.baking.storage

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import de.aaronoe.baking.BakingApp
import de.aaronoe.baking.R
import de.aaronoe.baking.model.Recipe
import de.aaronoe.baking.widget.RecipeWidgetProvider
import javax.inject.Inject


class RecipeInfoManager(val app: BakingApp) {

    @Inject
    lateinit var gson : Gson

    init {
        app.netComponent.inject(this)
    }

    companion object {
        val PREF_NAME = "recipe_prefs"
        val RECIPE_KEY = "recipe_key"
    }


    val recipeAdapter : TypeAdapter<Recipe> by lazy {
        gson.getAdapter(Recipe::class.java)
    }

    val sharedPrefs : SharedPreferences by lazy {
        app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getRecipe() : Recipe? {
        val recipe = sharedPrefs.getString(RECIPE_KEY, null)

        return recipe?.let {
            recipeAdapter.fromJson(recipe)
        }
    }

    private fun updateWidget() {
        val intent = Intent(app, RecipeWidgetProvider::class.java)

        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

        val appWidgetManager = AppWidgetManager.getInstance(app)
        val componentName = ComponentName(app, RecipeWidgetProvider::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

        app.sendBroadcast(intent)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredient_list)
    }

    fun saveRecipe(recipe : Recipe) {

        sharedPrefs.edit().apply {
            putString(RECIPE_KEY, recipeAdapter.toJson(recipe))
            apply()
        }

        updateWidget()
    }


}