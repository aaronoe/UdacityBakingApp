package de.aaronoe.baking;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.aaronoe.baking.ui.list.MainActivity_;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeListTest {

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Rule
    public ActivityTestRule<MainActivity_> mActivityTestRule =
            new ActivityTestRule<>(MainActivity_.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResources() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void idlingResourceTest() {
        onView(withRecyclerView(R.id.main_list_rv)
                .atPosition(0))
                .check(matches(isDisplayed()));

        onView(withRecyclerView(R.id.main_list_rv)
                .atPositionOnView(0, R.id.recipe_servings_tv))
                .check(matches(withText("8 Servings")));

        onView(withRecyclerView(R.id.main_list_rv)
                .atPositionOnView(0, R.id.recipe_name_tv))
                .check(matches(withText("Nutella Pie")));

        onView(withRecyclerView(R.id.main_list_rv)
                .atPosition(0))
                .perform(click());

        // DetailActivity now

        onView(withRecyclerView(R.id.master_list_rv)
                .atPosition(0))
                .check(matches(isDisplayed()));

        onView(withRecyclerView(R.id.master_list_rv)
                .atPositionOnView(1, R.id.step_number_tv))
                .check(matches(withText("1")));

        onView(withRecyclerView(R.id.master_list_rv)
                .atPosition(1))
                .perform(click());

        // Detail Step now

        onView(withId(R.id.detail_step_description_tv))
                .check(matches(isDisplayed()));

    }

    @After
    public void unregisterIdlingResources() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
