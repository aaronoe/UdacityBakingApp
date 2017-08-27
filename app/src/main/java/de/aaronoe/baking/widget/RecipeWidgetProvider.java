package de.aaronoe.baking.widget;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import javax.inject.Inject;

import de.aaronoe.baking.BakingApp;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.storage.RecipeInfoManager;
import de.aaronoe.baking.ui.detail.DetailActivity_;
import de.aaronoe.baking.ui.list.MainActivity_;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    @Inject
    RecipeInfoManager recipeInfoManager;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Recipe recipe = recipeInfoManager.getRecipe();

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_list_widget_view);
        if (recipe != null && recipe.getName() != null) {
            views.setTextViewText(R.id.recipe_name_tv, recipe.getName());
            Intent appIntent = new Intent(context, DetailActivity_.class);
            appIntent.putExtra(context.getString(R.string.INTENT_KEY_RECIPE), recipe);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity_.class);
            stackBuilder.addNextIntent(appIntent);

            PendingIntent appPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.widget_layout_main, appPendingIntent);
        } else {
            Intent appIntent = new Intent(context, MainActivity_.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_layout_main, appPendingIntent);
        }

        Intent remoteAdapterIntent = new Intent(context, IngredientListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_ingredient_list, remoteAdapterIntent);


        //views.setPendingIntentTemplate(R.id.widget_ingredient_list, appPendingIntent);
        views.setEmptyView(R.id.widget_ingredient_list, R.id.empty_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        ((BakingApp) context.getApplicationContext()).getNetComponent().inject(this);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

