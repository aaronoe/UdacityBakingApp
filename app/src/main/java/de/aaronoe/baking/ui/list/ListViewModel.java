package de.aaronoe.baking.ui.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import de.aaronoe.baking.BakingApp;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.model.remote.ApiService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by private on 7/31/17.
 *
 */

public class ListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Recipe>> recipeList;

    @Inject
    ApiService mApiService;

    public ListViewModel(Application bakingApp) {
        super(bakingApp);
        ((BakingApp) bakingApp).getNetComponent().inject(this);
    }

    MutableLiveData<List<Recipe>> getRecipes() {
        if (recipeList == null) {
            recipeList = new MutableLiveData<>();
            downloadRecipes();
        }
        return recipeList;
    }

    private void downloadRecipes() {

        mApiService
                .getRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Recipe>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(final List<Recipe> recipes) {
                        if (recipes != null) {
                            recipeList.setValue(recipes);
                        }
                    }
                });

    }

}
