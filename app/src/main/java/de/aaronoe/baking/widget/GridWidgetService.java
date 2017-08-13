package de.aaronoe.baking.widget;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import javax.inject.Inject;

import de.aaronoe.baking.BakingApp;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Ingredient;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.model.remote.ApiService;
import de.aaronoe.baking.storage.RecipeInfoManager;
import rx.Subscriber;

/**
 * Created by private on 8/13/17.
 *
 */

public class GridWidgetService extends RemoteViewsService {

    private static final String TAG = "GridWidgetService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory() called with: intent = [" + intent + "]");
        return new GridRemoteViewsFactory(getApplication());
    }

    public class GridRemoteViewsFactory implements RemoteViewsFactory {

        Recipe mRecipe;

        @Inject
        RecipeInfoManager recipeService;

        public GridRemoteViewsFactory(Application application) {
            ((BakingApp) application).getNetComponent().inject(this);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {

            mRecipe = recipeService.getRecipe();

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mRecipe == null ? 0 : mRecipe.getIngredients().size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            if (mRecipe == null || mRecipe.getIngredients().size() < i) return null;
            Ingredient ingredient = mRecipe.getIngredients().get(i);

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_ingredient_item);

            views.setTextViewText(R.id.widget_item_name_tv, ingredient.getIngredient());

            Intent fillIntent = new Intent();
            fillIntent.putExtra(getApplication().getString(R.string.INTENT_KEY_RECIPE), mRecipe);
            views.setOnClickFillInIntent(R.id.widget_item_container, fillIntent);
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
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
