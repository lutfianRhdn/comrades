package id.comrade.login;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import id.comrade.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void shouldDisableSigninButtonAtFirst() {
        onView(withId(R.id.btn_activity_login_login)).check(matches(not(isEnabled())));
    }

    @Test
    public void shouldDisableIfEmailIsEmpty() {
        onView(withId(R.id.et_activity_login_password))
                .perform(typeText("tes123455"));
        closeSoftKeyboard();

        onView(withId(R.id.btn_activity_login_login)).check(matches(not(isEnabled())));
    }

    @Test
    public void shouldDisableIfPasswordIsEmpty() {
        onView(withId(R.id.et_activity_login_email))
                .perform(typeText("email@example.com"));
        closeSoftKeyboard();

        onView(withId(R.id.btn_activity_login_login)).check(matches(not(isEnabled())));
    }

    @Test
    public void shouldEnableSigninButtonAfterFillingForm() {
        onView(withId(R.id.et_activity_login_email))
                .perform(typeText("email@example.com"));
        closeSoftKeyboard();

        onView(withId(R.id.et_activity_login_password))
                .perform(typeText("test123456"));
        closeSoftKeyboard();

        onView(withId(R.id.btn_activity_login_login)).check(matches(isEnabled()));
    }

}
