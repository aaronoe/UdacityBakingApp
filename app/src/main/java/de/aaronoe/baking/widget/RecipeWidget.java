package de.aaronoe.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import de.aaronoe.baking.R;
import de.aaronoe.baking.ui.detail.DetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    private static final String TAG = "RecipeWidget";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.d(TAG, "updateAppWidget() called with: context = [" + context + "], appWidgetManager = [" + appWidgetManager + "], appWidgetId = [" + appWidgetId + "]");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget_view);

        Intent remoteAdapterIntent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, remoteAdapterIntent);

        Intent appIntent = new Intent(context, DetailActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

