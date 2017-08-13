package de.aaronoe.baking.widget

import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import de.aaronoe.baking.BakingApp
import de.aaronoe.baking.R
import de.aaronoe.baking.db.RecipeDao
import de.aaronoe.baking.model.Recipe
import io.realm.RealmResults
import rx.Subscriber
import javax.inject.Inject

/**
 * Created by private on 8/6/17.
 *
 */


class GridWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return GridRemoteViewsFactory(application as BakingApp)
    }


    class GridRemoteViewsFactory(val application : BakingApp) : RemoteViewsService.RemoteViewsFactory {

        var recipeList : List<Recipe>? = null
        @Inject
        lateinit var recipeDao : RecipeDao

        init {
            application.netComponent.inject(this)
        }

        override fun onCreate() {
            onDataSetChanged()
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getItemId(id: Int): Long {
            return id.toLong()
        }

        override fun onDataSetChanged() {
            // we don't need to specify a threadpool here since we are already in a service
            recipeDao.getAllRecipesObservable()
                    .subscribe(object : Subscriber<RealmResults<Recipe>>() {
                        override fun onCompleted() {

                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }

                        override fun onNext(recipes: RealmResults<Recipe>) {
                            recipeList = recipes
                        }
                    })
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getViewAt(position: Int): RemoteViews {
            val recipe = recipeList?.get(position)

            val views = RemoteViews(application.packageName, R.layout.widget_ingredient_item)

            views.setTextViewText(R.id.widget_item_name_tv, recipe?.name)

            val fillIntent = Intent()
            fillIntent.putExtra(application.getString(R.string.INTENT_KEY_RECIPE), recipe)
            views.setOnClickFillInIntent(R.id.widget_item_container, fillIntent)

            return views
        }

        override fun getCount(): Int {
            if (recipeList != null) {
                return recipeList!!.size
            }
            return 0
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun onDestroy() {
        }
    }
}
