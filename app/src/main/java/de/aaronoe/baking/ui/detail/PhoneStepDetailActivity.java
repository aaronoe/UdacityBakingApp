package de.aaronoe.baking.ui.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Recipe;
import de.aaronoe.baking.model.Step;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by private on 8/5/17.
 *
 */

@EActivity(R.layout.step_detail_activity)
public class PhoneStepDetailActivity extends AppCompatActivity {

    @ViewById(R.id.detail_step_circle_indicator)
    CircleIndicator pagerIndicator;

    @ViewById(R.id.detail_viewpager)
    ViewPager viewPager;

    Recipe mRecipe;

    int selectedItemIndex = 0;
    StepPagerAdapter stepAdapter;


    @AfterViews
    void init() {

        if (getIntent().hasExtra(getString(R.string.INTENT_KEY_STEP_LIST))) {
            mRecipe = getIntent().getParcelableExtra(getString(R.string.INTENT_KEY_STEP_LIST));
            selectedItemIndex = getIntent().getIntExtra(getString(R.string.INTENT_KEY_POSITION), 0);
            stepAdapter = new StepPagerAdapter(getSupportFragmentManager(), mRecipe.getSteps());
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            viewPager.setAdapter(stepAdapter);
            viewPager.setCurrentItem(selectedItemIndex);
            pagerIndicator.setViewPager(viewPager);
        }
    }


    private class StepPagerAdapter extends FragmentStatePagerAdapter {

        List<Step> stepList;

        StepPagerAdapter(FragmentManager fragmentManager, List<Step> stepList) {
            super(fragmentManager);
            this.stepList = stepList;
        }

        @Override
        public Fragment getItem(int position) {
            //return StepDetailFragment_.builder().setmStep(stepList.get(position)).build();
            return null;
        }

        @Override
        public int getCount() {
            return stepList == null ? 0 : stepList.size();
        }

    }

}
