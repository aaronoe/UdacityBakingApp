package de.aaronoe.baking.ui.list;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import de.aaronoe.baking.BakingApp;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.model.remote.ApiService;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements ListContract.View, RecipeAdapter.RecipeClickListener {

    @Inject
    ApiService apiService;

    @ViewById(R.id.main_list_rv)
    RecyclerView mainListRv;
    @ViewById(R.id.main_list_pb)
    ProgressBar mainListPb;

    ListContract.Presenter mPresenter;

    @AfterViews
    void init() {
        ((BakingApp) getApplication()).getNetComponent().inject(this);

        mPresenter = new ListPresenterImpl(this, apiService);
        mPresenter.getRecipes();
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
    public void clickOnRecipe(int id) {
        Toast.makeText(this, "Clicked: " + id, Toast.LENGTH_SHORT).show();
    }
}
