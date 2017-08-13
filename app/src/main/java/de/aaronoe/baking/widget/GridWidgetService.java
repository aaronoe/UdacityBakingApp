package de.aaronoe.baking.widget;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.aaronoe.baking.BakingApp;
import de.aaronoe.baking.R;
import de.aaronoe.baking.db.RecipeDao;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.model.remote.ApiService;
import io.realm.Realm;
import io.realm.RealmResults;
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

        RecipeDao mRecipeDao;
        List<Recipe> recipeList;

        @Inject
        ApiService apiService;

        public GridRemoteViewsFactory(Application application) {
            ((BakingApp) application).getNetComponent().inject(this);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {

            apiService.getRecipes()
                    .subscribe(new Subscriber<List<Recipe>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<Recipe> recipes) {
                            recipeList = recipes;
                        }
                    });

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return recipeList == null ? 0 : recipeList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            Log.d(TAG, "getViewAt() called with: i = [" + i + "]" + recipeList.size());
            if (recipeList == null || recipeList.size() < i) return null;
            Recipe recipe = recipeList.get(i);

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_ingredient_item);

            views.setTextViewText(R.id.widget_item_name_tv, recipe.getName());

            Intent fillIntent = new Intent();
            fillIntent.putExtra(getApplication().getString(R.string.INTENT_KEY_RECIPE), recipe);
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
