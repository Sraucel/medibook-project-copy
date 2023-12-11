package com.example.medibook;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class DoctorRegisterActivityTest {

    @Rule
    public ActivityTestRule<DoctorRegisterActivity> activityRule = new ActivityTestRule<>(DoctorRegisterActivity.class);

    @Test
    public void validateData_withInvalidInput_ShowsWarnings() {
        // Enter invalid input
        onView(withId(R.id.firstName)).perform(typeText(""));
        onView(withId(R.id.lastName)).perform(typeText(""));
        onView(withId(R.id.emailAddress)).perform(typeText(""));
        onView(withId(R.id.editTextTextPassword)).perform(typeText(""));
        onView(withId(R.id.phoneNumber)).perform(typeText(""));
        onView(withId(R.id.address)).perform(typeText(""));
        onView(withId(R.id.employeeNumber)).perform(typeText(""));
        onView(withId(R.id.specialties)).perform(typeText(""));

        // Attempt to register
        onView(withId(R.id.registerPatientButton)).perform(click());

        // Verify that warnings are displayed
        onView(withId(R.id.textWarnFirstName)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.textWarnLastName)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.textWarnEmail)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.textWarnPassword)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.textWarnPhoneNumber)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.textWarnAddress)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.textWarnEmployeeNumber)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(withId(R.id.textWarnSpecialties)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
    }

    @Test
    public void validateData_withValidInput_HidesWarnings() {
        // Enter valid input
        onView(withId(R.id.firstName)).perform(typeText("John"));
        onView(withId(R.id.lastName)).perform(typeText("Doe"));
        onView(withId(R.id.emailAddress)).perform(typeText("johndoe@example.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText("password123"));
        onView(withId(R.id.phoneNumber)).perform(typeText("1234567890"));
        onView(withId(R.id.address)).perform(typeText("123 Main St"));
        onView(withId(R.id.employeeNumber)).perform(typeText("123456"));
        onView(withId(R.id.specialties)).perform(typeText("Cardiology"));

        // Attempt to register
        onView(withId(R.id.registerPatientButton)).perform(click());

        // Verify that warnings are hidden
        onView(withId(R.id.textWarnFirstName)).check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.textWarnLastName)).check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.textWarnEmail)).check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.textWarnPassword)).check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.textWarnPhoneNumber)).check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.textWarnAddress)).check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.textWarnEmployeeNumber)).check(matches(withEffectiveVisibility(Visibility.GONE)));
        onView(withId(R.id.textWarnSpecialties)).check(matches(withEffectiveVisibility(Visibility.GONE)));
    }
}
