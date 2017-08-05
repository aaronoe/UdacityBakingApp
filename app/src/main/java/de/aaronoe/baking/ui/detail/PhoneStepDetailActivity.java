package de.aaronoe.baking.ui.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import de.aaronoe.baking.R;
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

    List<Step> mStepList;
    int selectedItemIndex = 0;

    @AfterViews
    void init() {

        if (getIntent().hasExtra(getString(R.string.INTENT_KEY_STEP_LIST))) {
            mStepList = getIntent().getParcelableArrayListExtra(getString(R.string.INTENT_KEY_STEP_LIST));
            selectedItemIndex = getIntent().getIntExtra(getString(R.string.INTENT_KEY_STEP_LIST), 0);
            StepPagerAdapter stepAdapter = new StepPagerAdapter(getSupportFragmentManager(), mStepList);
            viewPager.setAdapter(stepAdapter);
            viewPager.setCurrentItem(selectedItemIndex);
            pagerIndicator.setViewPager(viewPager);
        }
    }

    class StepPagerAdapter extends FragmentStatePagerAdapter {

        List<Step> stepList;

        public StepPagerAdapter(FragmentManager fragmentManager, List<Step> stepList) {
            super(fragmentManager);
            this.stepList = stepList;
        }

        @Override
        public Fragment getItem(int position) {
            return StepDetailFragment_.builder().setmStep(stepList.get(position)).build();
        }

        @Override
        public int getCount() {
            return stepList == null ? 0 : stepList.size();
        }

    }


}
