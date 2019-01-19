package com.example.haris.finalproject

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.haris.finalproject.activity.MainActivity
import com.example.haris.finalproject.R.id.rv_last_match
import com.example.haris.finalproject.utils.Constants
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LastMatchFragmentTest{

    private var idlingResource: LastMatchIdlingResource? = null
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun setUp() {
        idlingResource = LastMatchIdlingResource(Constants.IDLE_TIME) // mActivityRule.getActivity().getIdlingResource();
        //Espresso.registerIdlingResources(idlingResource)
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown(){
        //Espresso.unregisterIdlingResources(idlingResource)
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId( rv_last_match))
            .check(matches(isDisplayed()))
        onView(withId( rv_last_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15))
    }
    @Test
    fun addFavorites(){
        onView(withId(R.id.rv_last_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                10,
                ViewActions.click()
            )
        )
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Espresso.pressBack()
    }

    @Test
    fun removeFavorites(){
        onView(withId(R.id.rv_last_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                10,
                ViewActions.click()
            )
        )
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Espresso.pressBack()
    }

}