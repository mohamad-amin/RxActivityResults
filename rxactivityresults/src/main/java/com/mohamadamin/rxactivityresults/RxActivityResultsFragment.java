package com.mohamadamin.rxactivityresults;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by Mohamad Amin Mohamadi (mohammadi.mohamadamin@gmail.com) on 8/23/17.
 */

public class RxActivityResultsFragment extends Fragment {

    private static final int ACTIVITY_RESULT_REQUEST_CODE = 1692;
    private PublishSubject<ActivityResult> activityResults;
    private boolean logging;

    public RxActivityResultsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    void startActivityForResult(Intent intent) {
        startActivityForResult(intent, ACTIVITY_RESULT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_RESULT_REQUEST_CODE) {
            log("onActivityResult: requestCode -> " +
                    requestCode + " and resultCode -> " + resultCode + " and data -> " + data);
            if (activityResults == null) {
                Log.e("RxActivityResults", "RxPermissions.onRequestPermissionsResult" +
                        " invoked but didn't find the corresponding permission request.");
                return;
            }
            activityResults.onNext(new ActivityResult(resultCode, data));
            log("onActivityResult: onNext");
            activityResults.onComplete();
            activityResults = null;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    PublishSubject<ActivityResult> getSubject() {
        return activityResults;
    }

    void setSubject(PublishSubject<ActivityResult> subject) {
        activityResults = subject;
    }

    void setLogging(boolean logging) {
        this.logging = logging;
    }

    private void log(String message) {
        if (logging) {
            Log.d(RxActivityResults.TAG, message);
        }
    }

}
