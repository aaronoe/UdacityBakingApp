package de.aaronoe.baking.ui.list;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import de.aaronoe.baking.AppCompatLifeCycleActivity;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.testing.SimpleIdlingResource;
import de.aaronoe.baking.ui.detail.DetailActivity_;
import okhttp3.OkHttpClient;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatLifeCycleActivity implements RecipeAdapter.RecipeClickListener {

    @ViewById(R.id.main_list_rv)
    RecyclerView mainListRv;
    @ViewById(R.id.main_list_pb)
    ProgressBar mainListPb;

    @Inject
    OkHttpClient mOkhttpClient;

    @Nullable
    SimpleIdlingResource mIdlingResource;

    @AfterViews
    void init() {

        getIdlingResource();

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        ViewModelProviders.of(this).get(ListViewModel.class).getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                showItems(recipes);

                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }
            }
        });

    }


    public void showItems(final List<Recipe> recipes) {

        mainListPb.setVisibility(View.INVISIBLE);
        mainListRv.setVisibility(View.VISIBLE);

        RecipeAdapter adapter = new RecipeAdapter(this);
        adapter.setRecipeList(recipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mainListRv.setAdapter(adapter);
        mainListRv.setLayoutManager(layoutManager);
    }


    @Override
    public void clickOnRecipe(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity_.class);
        intent.putExtra(getString(R.string.INTENT_KEY_RECIPE), recipe);
        startActivity(intent);
    }

    @Nullable
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
