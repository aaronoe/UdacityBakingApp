package de.aaronoe.baking.ui.detail;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import butterknife.BindView;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Ingredient;
import de.aaronoe.baking.model.Recipe;

@EActivity(R.layout.activity_detail)
public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.master_list_rv)
    RecyclerView masterListRv;

    FrameLayout detailFrameLayout;
    Recipe mRecipe;
    MasterDetailFragment detailFragment;


    boolean isTwoPaneLayout = false;

    @AfterViews
    void init() {

        if (getIntent().hasExtra(getString(R.string.INTENT_KEY_RECIPE))) {
            mRecipe = getIntent().getParcelableExtra(getString(R.string.INTENT_KEY_RECIPE));
        } else {
            // In case no recipe was set as an extra we should return and show an error
            Toast.makeText(this, "No Recipe found", Toast.LENGTH_SHORT).show();
            return;
        }

        // This condition is true when the width of the screen is greater than 600dp
        if (findViewById(R.id.detail_frame) != null) {

            isTwoPaneLayout = true;
            detailFragment = MasterDetailFragment_.builder().setIngredients((ArrayList<Ingredient>) mRecipe.getIngredients()).build();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_frame, detailFragment)
                    .commit();

            // By default set ingredients

        } else {

        }

    }

}
