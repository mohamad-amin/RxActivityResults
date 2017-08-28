package com.mohamadamin.rxactivityresults.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.mohamadamin.rxactivityresults.ActivityResult;
import com.mohamadamin.rxactivityresults.RxActivityResults;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private ImageView imageView;
    private TextView loginInfoText;
    private FloatingActionButton pickImageFab;

    private CompositeDisposable disposables;
    private RxActivityResults rxActivityResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R    .layout.activity_main);
        initializeViews();

        rxActivityResults = new RxActivityResults(this);
        disposables = new CompositeDisposable();

        setUpLogin();
        setUpImagePicker();

    }

    private void setUpLogin() {

        Disposable disposable = RxView.clicks(loginButton)
                .doOnNext(o -> Log.d("TAGS", "Hello"))
                .flatMap(ignoredObject -> rxActivityResults.start(getLoginIntent()))
                .filter(activityResult -> activityResult.getResultCode() == RESULT_OK)
                .map(ActivityResult::getData)
                .subscribe(intent -> {

                    String lastName = intent.getStringExtra(LoginActivity.LAST_NAME);
                    String firstName = intent.getStringExtra(LoginActivity.FIRST_NAME);
                    String emailAddress = intent.getStringExtra(LoginActivity.EMAIL_ADDRESS);

                    String result = getString(R.string.first_name) + ": " + firstName + "\n"
                            + getString(R.string.last_name) + ": " + lastName + "\n"
                            + getString(R.string.email_address) + ": " + emailAddress + "\n";

                    loginInfoText.setText(result);

                });

        disposables.add(disposable);

    }

    private void setUpImagePicker() {

        Disposable disposable = RxView.clicks(pickImageFab)
                .flatMap(ignoredObject -> rxActivityResults.start(getPickImageIntent()))
                .observeOn(Schedulers.computation())
                .filter(activityResult -> activityResult.getResultCode() == RESULT_OK)
                .map(ActivityResult::getData)
                .filter(intent -> intent.getData() != null)
                .map(Intent::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageView::setImageURI);

        disposables.add(disposable);

    }

    private void initializeViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.input_image);
        loginButton = (Button) findViewById(R.id.button_login);
        pickImageFab = (FloatingActionButton) findViewById(R.id.fab);
        loginInfoText = (TextView) findViewById(R.id.text_login_info);

    }

    private Intent getLoginIntent() {
        return new Intent(this, LoginActivity.class);
    }

    public static Intent getPickImageIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        return photoPickerIntent;
    }


    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }

}
