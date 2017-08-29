package com.mohamadamin.rxactivityresults;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mohamad Amin Mohamadi (mohamadamin@cafebazaar.ir) on 8/28/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class RxActivityResultsTest {

    private Activity activity;
    private RxActivityResults rxActivityResults;
    private final Object TRIGGER = new Object();

    @Before
    public void setUp() {

        ActivityController<Activity> activityController =
                Robolectric.buildActivity(Activity.class);
        activity = spy(activityController.setup().get());

        rxActivityResults = spy(new RxActivityResults(activity));
        rxActivityResults.mRxActivityResultsFragment =
                spy(rxActivityResults.mRxActivityResultsFragment);

        when(rxActivityResults.mRxActivityResultsFragment.getActivity())
                .thenReturn(activity);

    }

    private Observable<Object> trigger() {
        return Observable.just(TRIGGER);
    }

    @Test
    public void composer() throws Exception {

        Intent someIntent = new Intent();
        ActivityResult testResult = new ActivityResult(Activity.RESULT_OK, Activity.RESULT_OK, someIntent);

        TestObserver<ActivityResult> composerTest =
                trigger().compose(rxActivityResults.composer(someIntent)).test();

        rxActivityResults.onActivityResult(testResult.getResultCode(), someIntent);

        composerTest.assertNoErrors()
                .assertSubscribed()
                .assertComplete()
                .assertValueCount(1)
                .assertValue(testResult);

    }

    @Test(expected = IllegalArgumentException.class)
    public void requestNullIntent() throws Exception {

        TestObserver<ActivityResult> composerTest =
                trigger().compose(rxActivityResults.composer(null)).test();

        composerTest.assertError(IllegalArgumentException.class)
                .assertSubscribed()
                .assertComplete()
                .assertValueCount(0);

    }

    @Test
    public void ensureOkResult() throws Exception {

        Intent someIntent = new Intent();
        ActivityResult testResult = new ActivityResult(Activity.RESULT_OK, Activity.RESULT_OK, someIntent);

        TestObserver<Boolean> composerTest =
                trigger().compose(rxActivityResults.ensureOkResult(someIntent)).test();

        rxActivityResults.onActivityResult(testResult.getResultCode(), someIntent);

        composerTest.assertNoErrors()
                .assertSubscribed()
                .assertComplete()
                .assertValueCount(1)
                .assertValue(testResult.isOk());

    }

    @Test
    public void start() throws Exception {

        Intent someIntent = new Intent();
        ActivityResult testResult = new ActivityResult(Activity.RESULT_OK, Activity.RESULT_OK, someIntent);

        TestObserver<ActivityResult> composerTest =
                rxActivityResults.start(someIntent).test();

        rxActivityResults.onActivityResult(testResult.getResultCode(), someIntent);

        verify(rxActivityResults.mRxActivityResultsFragment)
                .onActivityResult(any(Integer.class), eq(testResult.getResultCode()), any(Intent.class));

        composerTest.assertNoErrors()
                .assertSubscribed()
                .assertComplete()
                .assertValueCount(1)
                .assertValue(testResult);

    }

}