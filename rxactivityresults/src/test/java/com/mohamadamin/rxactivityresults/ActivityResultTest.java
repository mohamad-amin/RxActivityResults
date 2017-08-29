package com.mohamadamin.rxactivityresults;

import android.app.Activity;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mohamad Amin Mohamadi (mohamadamin@cafebazaar.ir) on 8/28/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class ActivityResultTest {

    @Mock
    Intent intent;

    private int resultCode = 12;
    private ActivityResult activityResult;

    @Before
    public void setUp() {
        activityResult = new ActivityResult(Activity.RESULT_OK, resultCode, intent);
    }

    @Test
    public void equalsTrue() throws Exception {
        ActivityResult testResult = new ActivityResult(Activity.RESULT_OK, resultCode, intent);
        assertTrue(activityResult.equals(testResult));
        assertTrue(activityResult.equals(activityResult));
    }

    @Test
    public void equalsFalse() throws Exception {
        ActivityResult testResult = new ActivityResult(Activity.RESULT_OK, resultCode - 1, intent);
        assertFalse(activityResult.equals(testResult));
        assertFalse(activityResult.equals(intent));
    }

    @Test
    public void isOkTest() throws Exception {

        ActivityResult faultyResult = new ActivityResult(Activity.RESULT_OK, resultCode - 1, intent);
        assertFalse(faultyResult.isOk());

        ActivityResult okResult = new ActivityResult(Activity.RESULT_OK, Activity.RESULT_OK, intent);
        assertTrue(okResult.isOk());

    }

    @Test
    public void toStringTest() throws Exception {
        String testResult = "ActivityResult { ResultCode" +
                " = " + resultCode + ", Data = " + intent + " }";
        assertEquals(activityResult.toString(), testResult);
    }

    @Test
    public void getData() {
        assertEquals(activityResult.getData(), intent);
    }

    @Test
    public void getResultCode() {
        assertEquals(activityResult.getResultCode(), resultCode);
    }

}