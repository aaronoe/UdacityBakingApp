package de.aaronoe.baking.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import de.aaronoe.baking.AppCompatLifeCycleActivity;
import de.aaronoe.baking.BakingApp;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.model.remote.ApiService;
import de.aaronoe.baking.ui.detail.DetailActivity_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatLifeCycleActivity implements ListContract.View, RecipeAdapter.RecipeClickListener {

    @Inject
    ApiService apiService;

    @ViewById(R.id.main_list_rv)
    RecyclerView mainListRv;
    @ViewById(R.id.main_list_pb)
    ProgressBar mainListPb;

    private static final String TAG = "MainActivity";

    @AfterViews
    void init() {
        ((BakingApp) getApplication()).getNetComponent().inject(this);

        ViewModelProviders.of(this).get(ListViewModel.class).getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.d(TAG, "onChanged() called with: recipes = [" + recipes + "]");
                showItems(recipes);
            }
        });

    }


    @Override
    public void showItems(List<Recipe> recipes) {
        mainListPb.setVisibility(View.INVISIBLE);
        mainListRv.setVisibility(View.VISIBLE);

        RecipeAdapter adapter = new RecipeAdapter(this);
        adapter.setRecipeList(recipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mainListRv.setAdapter(adapter);
        mainListRv.setLayoutManager(layoutManager);
    }

    @Override
    public void showError() {
        mainListPb.setVisibility(View.INVISIBLE);
        mainListRv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void clickOnRecipe(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity_.class);
        intent.putExtra(getString(R.string.INTENT_KEY_RECIPE), recipe);
        startActivity(intent);
    }
}
