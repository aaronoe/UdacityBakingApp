package de.aaronoe.baking.ui.list;

import android.widget.Toast;

import java.util.List;

import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.model.remote.ApiService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by private on 7/31/17.
 *
 */

public class ListPresenterImpl implements ListContract.Presenter {

    private ApiService mApiService;
    private ListContract.View mView;

    public ListPresenterImpl(ListContract.View view, ApiService apiService) {
        mApiService = apiService;
        mView = view;
    }

    @Override
    public void getRecipes() {

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
                        mView.showError();
                    }

                    @Override
                    public void onNext(List<Recipe> recipes) {
                        if (recipes == null) {
                            mView.showError();
                            return;
                        }
                        mView.showItems(recipes);
                    }
                });
    }


}
