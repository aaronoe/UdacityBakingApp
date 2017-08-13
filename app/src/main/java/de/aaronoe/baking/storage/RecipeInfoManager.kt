package de.aaronoe.baking.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import de.aaronoe.baking.BakingApp
import de.aaronoe.baking.model.Recipe
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

    fun updateWidget() {

    }

    fun saveRecipe(recipe : Recipe) {

        sharedPrefs.edit().apply {
            putString(RECIPE_KEY, recipeAdapter.toJson(recipe))
            apply()
        }

        updateWidget()
    }


}