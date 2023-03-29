package id.comrade;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import id.comrade.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldDisplayHomeFirst() {
        onView(withText("John Doe")).check(matches(isDisplayed()));
        onView(withText("20 menit yang lalu")).check(matches(isDisplayed()));
        onView(withText("Apakah HIV dapat menular melalui sentuhan dengan yang lainnya?"))
                .check(matches(isDisplayed()));
    }

}
