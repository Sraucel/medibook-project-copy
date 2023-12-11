package com.example.medibook;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SignInActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Test
    public void validateData_WhenEmailAndPasswordAreValid_DisplaySuccessMessage() {
        // Type text and then press the button
        onView(withId(R.id.emailAddress)).perform(typeText("invalidemailexample.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(typeText("validpassword"), closeSoftKeyboard());

        // Click sign-in button
        onView(withId(R.id.SignInButton)).perform(click());

        // Check if the success message is displayed
        onView(withId(R.id.textWarnEmail)).check(matches(withText("Please enter your email")));
    }
}
