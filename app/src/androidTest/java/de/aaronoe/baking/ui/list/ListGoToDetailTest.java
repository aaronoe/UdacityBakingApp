package de.aaronoe.baking.ui.list;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.aaronoe.baking.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static de.aaronoe.baking.RecipeListTest.withRecyclerView;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class ListGoToDetailTest {

    IdlingResource mIdlingResource;

    @Rule
    public IntentsTestRule<MainActivity_> mActivityRule = new IntentsTestRule<>(
            MainActivity_.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Before
    public void registerIdlingResources() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void idlingResourceTest() {
        onView(withRecyclerView(R.id.main_list_rv).atPosition(1)).perform(click());

        intended(hasExtraWithKey("INTENT_KEY_RECIPE"));
    }

    @After
    public void unregisterIdlingResources() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
