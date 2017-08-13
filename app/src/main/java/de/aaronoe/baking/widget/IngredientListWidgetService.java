package de.aaronoe.baking.widget;

import android.app.Application;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import javax.inject.Inject;

import de.aaronoe.baking.BakingApp;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Ingredient;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.storage.RecipeInfoManager;

/**
 * Created by private on 8/13/17.
 *
 */

public class IngredientListWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(getApplication());
    }

    public class GridRemoteViewsFactory implements RemoteViewsFactory {

        Recipe mRecipe;

        @Inject
        RecipeInfoManager recipeService;

        GridRemoteViewsFactory(Application application) {
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

            views.setTextViewText(R.id.widget_ingredient_name_tv, ingredient.getIngredient());
            views.setTextViewText(R.id.widget_ingredient_quantity_tv, ingredient.getQuantity() + ingredient.getMeasure());

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
