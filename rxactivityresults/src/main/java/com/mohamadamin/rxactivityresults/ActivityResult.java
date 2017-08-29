package com.mohamadamin.rxactivityresults;

import android.content.Intent;

/**
 * Created by Mohamad Amin Mohamadi (mohammadi.mohamadamin@gmail.com) on 8/23/17.
 */

public class ActivityResult {

    private final Intent data;
    private final int resultCode;
    private final int okResultCode;

    public ActivityResult(int okResultCode, int resultCode, Intent data) {
        this.data = data;
        this.resultCode = resultCode;
        this.okResultCode = okResultCode;
    }

    public Intent getData() {
        return data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public boolean isOk() {
        return resultCode == okResultCode;
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