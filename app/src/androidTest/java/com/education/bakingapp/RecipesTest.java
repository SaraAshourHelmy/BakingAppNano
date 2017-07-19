package com.education.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.education.bakingapp.views.activity.RecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;


/**
 * Created by Sara on 7/18/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipesTest {

    @Rule
    public ActivityTestRule<RecipeActivity> activityTestRule = new
            ActivityTestRule<RecipeActivity>(RecipeActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void setResource() {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void testActivity() {
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
    }

    @After
    public void releaseResource() {
        if (idlingResource != null)
            Espresso.unregisterIdlingResources(idlingResource);
    }
}
