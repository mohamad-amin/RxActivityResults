package com.mohamadamin.rxactivityresults;

import android.content.Intent;

/**
 * Created by Mohamad Amin Mohamadi (mohammadi.mohamadamin@gmail.com) on 8/23/17.
 */

public class ActivityResult {

    private final Intent data;
    private final int resultCode;

    public ActivityResult(int resultCode, Intent data) {
        this.data = data;
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public int getResultCode() {
        return resultCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivityResult) {
            if (this == obj) {
                return true;
            }
            ActivityResult activityResult = (ActivityResult) obj;
            return activityResult.data == data
                    && activityResult.resultCode == resultCode;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "ActivityResult { ResultCode = " +
                resultCode + ", Data = " + data + " }";
    }

}