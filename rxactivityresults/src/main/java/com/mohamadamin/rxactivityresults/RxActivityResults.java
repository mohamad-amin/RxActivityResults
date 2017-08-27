package com.mohamadamin.rxactivityresults;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Mohamad Amin Mohamadi (mohammadi.mohamadamin@gmail.com) on 8/23/17.
 */

public class RxActivityResults {

    static final String TAG = "RxActivityResults";
    static final Object TRIGGER = new Object();
    RxActivityResultsFragment mRxActivityResultsFragment;

    public RxActivityResults(@NonNull Activity activity) {
        this.mRxActivityResultsFragment = getRxActivityResultsFragment(activity);
    }

    private RxActivityResultsFragment getRxActivityResultsFragment(Activity activity) {
        RxActivityResultsFragment fragment = findRxActivityResultsFragment(activity);
        if (fragment == null) {
            fragment = new RxActivityResultsFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(fragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    private RxActivityResultsFragment findRxActivityResultsFragment(Activity activity) {
        return (RxActivityResultsFragment)
                activity.getFragmentManager().findFragmentByTag("RxPermissions");
    }

    public <T> ObservableTransformer<T, ActivityResult> composer(final Intent intent) {
        return new ObservableTransformer<T, ActivityResult>() {
            @Override
            public ObservableSource<ActivityResult> apply(@NonNull Observable<T> upstream) {
                return request(upstream, intent);
            }
        };
    }

    public <T> ObservableTransformer<T, Boolean> ensureOkResult(final Intent intent) {
        return new ObservableTransformer<T, Boolean>() {
            @Override
            public ObservableSource<Boolean> apply(@NonNull Observable<T> upstream) {
                return request(upstream, intent).map(new Function<ActivityResult, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull ActivityResult activityResult) throws Exception {
                        return activityResult.getResultCode() == Activity.RESULT_OK;
                    }
                });
            }
        };
    }

    public Observable<ActivityResult> start(Intent intent) {
        return Observable.just(TRIGGER).compose(this.composer(intent));
    }

    private Observable<ActivityResult> request(Observable<?> trigger, final Intent intent) {
        if (intent != null) {
            return trigger.flatMap(new Function<Object, Observable<ActivityResult>>() {
                @Override
                public Observable<ActivityResult> apply(@NonNull Object o) throws Exception {
                    return requestImplementation(intent);
                }
            });
        } else {
            throw new IllegalArgumentException("RxActivityResults.request a non null input intent");
        }
    }

    private Observable<ActivityResult> requestImplementation(final Intent intent) {
        PublishSubject<ActivityResult> subject = mRxActivityResultsFragment.getSubject();
        if (subject == null) {
            subject = PublishSubject.create();
            mRxActivityResultsFragment.setSubject(subject);
            mRxActivityResultsFragment.startActivityForResult(intent);
        }
        return subject;
    }

    public void setLogging(boolean logging) {
        mRxActivityResultsFragment.setLogging(logging);
    }

}
