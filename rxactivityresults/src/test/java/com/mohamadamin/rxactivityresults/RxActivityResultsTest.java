package com.mohamadamin.rxactivityresults;

import android.app.Activity;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import io.reactivex.Observable;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by Mohamad Amin Mohamadi (mohamadamin@cafebazaar.ir) on 8/28/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class RxActivityResultsTest {

    private Activity activity;
    private RxActivityResults rxActivityResults;

    @Before
    public void setUp() {

        ActivityController<Activity> activityController = Robolectric.buildActivity(Activity.class);
        activity = spy(activityController.setup().get());

        rxActivityResults = spy(new RxActivityResults(activity));
        rxActivityResults.mRxActivityResultsFragment = spy(rxActivityResults.mRxActivityResultsFragment);

        when(rxActivityResults.mRxActivityResultsFragment.getActivity())
                .thenReturn(activity);

    }

    private Observable<Object> trigger() {
        return Observable.just(null);
    }

    @Test
    public void composer() throws Exception {

    }

    @Test
    public void ensureOkResult() throws Exception {

    }

    @Test
    public void start() throws Exception {

    }

    @Test
    public void setLogging() throws Exception {

    }

}