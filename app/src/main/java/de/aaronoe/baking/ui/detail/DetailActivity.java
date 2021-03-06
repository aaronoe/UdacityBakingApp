package de.aaronoe.baking.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import de.aaronoe.baking.BakingApp;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.storage.RecipeInfoManager;

@EActivity(R.layout.activity_detail)
public class DetailActivity extends AppCompatActivity implements DetailNavigationAdapter.StepClickCallback {

    @ViewById(R.id.master_list_rv)
    RecyclerView masterListRv;

    @Inject
    RecipeInfoManager recipeInfoManager;

    @InstanceState
    Recipe mRecipe;
    StepDetailFragment detailFragment;


    boolean isTwoPaneLayout = false;

    @AfterViews
    void init() {
        ((BakingApp) getApplication()).getNetComponent().inject(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        isTwoPaneLayout = findViewById(R.id.detail_frame) != null;

        if (getIntent().hasExtra(getString(R.string.INTENT_KEY_RECIPE))) {
            mRecipe = getIntent().getParcelableExtra(getString(R.string.INTENT_KEY_RECIPE));
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mRecipe.getName());
            }
        } else {
            // In case no recipe was set as an extra we should return and show an error
            Toast.makeText(this, "No Recipe found", Toast.LENGTH_SHORT).show();
            return;
        }

        DetailNavigationAdapter adapter = new DetailNavigationAdapter(this, this);
        adapter.setStepList(mRecipe.getSteps(), mRecipe.getIngredients());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        masterListRv.setAdapter(adapter);
        masterListRv.setLayoutManager(layoutManager);

        if (isTwoPaneLayout) {
            updateDetailLayout(0);
        }

    }

    public void updateDetailLayout(int i) {
        // This condition is true when the width of the screen is greater than 600dp
        detailFragment = StepDetailFragment_.builder().setmStep(mRecipe.getSteps().get(i)).setIsTablet(true).build();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_frame, detailFragment)
                .commit();
    }

    @Override
    public void onClickStep(int position) {
        if (isTwoPaneLayout) {
            updateDetailLayout(position - 1);
        } else {
            Intent intentToDetailActivity = new Intent(this, PhoneStepDetailActivity_.class);
            intentToDetailActivity.putExtra(getString(R.string.INTENT_KEY_STEP_LIST), mRecipe);
            intentToDetailActivity.putExtra(getString(R.string.INTENT_KEY_POSITION), position);
            startActivity(intentToDetailActivity);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recipe_in_widget:
                recipeInfoManager.saveRecipe(mRecipe);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
