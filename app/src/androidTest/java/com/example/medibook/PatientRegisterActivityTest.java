package com.example.medibook;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class PatientRegisterActivityTest {

    @Rule
    public ActivityScenarioRule<PatientRegisterActivity> activityRule = new ActivityScenarioRule<>(PatientRegisterActivity.class);

    @Test
    public void validateData_WithEmptyFields_ShowsWarnings() {
        // Type text and then press the button.
        onView(withId(R.id.firstName)).perform(typeText(""));
        onView(withId(R.id.lastName)).perform(typeText(""));
        onView(withId(R.id.emailAddress)).perform(typeText(""));
        onView(withId(R.id.editTextTextPassword)).perform(typeText(""));
        onView(withId(R.id.phoneNumber)).perform(typeText(""));
        onView(withId(R.id.address)).perform(typeText(""));
        onView(withId(R.id.healthCard)).perform(typeText(""));

        // Close soft keyboard
        onView(withId(R.id.registerPatientButton)).perform(click());

        // Check if the warnings are displayed
        onView(withId(R.id.textWarnFirstName)).check(matches(isDisplayed()));
        onView(withId(R.id.textWarnLastName)).check(matches(isDisplayed()));
        onView(withId(R.id.textWarnEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.textWarnPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.textWarnPhoneNumber)).check(matches(isDisplayed()));
        onView(withId(R.id.textWarnAddress)).check(matches(isDisplayed()));
        onView(withId(R.id.textWarnHealth)).check(matches(isDisplayed()));
    }


}
