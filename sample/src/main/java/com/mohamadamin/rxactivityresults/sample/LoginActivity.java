package com.mohamadamin.rxactivityresults.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends AppCompatActivity {

    public static final String LAST_NAME = "LastName";
    public static final String FIRST_NAME = "FirstName";
    public static final String EMAIL_ADDRESS = "EmailAddress";

    private EditText lastNameEditText;
    private EditText firstNameEditText;
    private EditText emailAddressEditText;

    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initializeViews();
        disposables = new CompositeDisposable();

        Disposable disposable = RxView.clicks(findViewById(R.id.fab))
                .map(ignoredObject ->
                        !TextUtils.isEmpty(firstNameEditText.getText().toString())
                                && !TextUtils.isEmpty(lastNameEditText.getText().toString())
                                && !TextUtils.isEmpty(lastNameEditText.getText().toString())
                )
                .subscribe(isInputValid -> {
                    if (isInputValid) {
                        Intent responseData = new Intent();
                        responseData.putExtra(LAST_NAME, lastNameEditText.getText().toString());
                        responseData.putExtra(FIRST_NAME, firstNameEditText.getText().toString());
                        responseData.putExtra(EMAIL_ADDRESS, emailAddressEditText.getText().toString());
                        setResult(RESULT_OK, responseData);
                        finish();
                    } else {
                        Snackbar snackbar = Snackbar.make(
                                findViewById(R.id.login_content),
                                R.string.all_fields_required,
                                Snackbar.LENGTH_SHORT);
                        snackbar.setAction(android.R.string.ok, view -> {}).show();
                    }
                });

        disposables.add(disposable);

    }

    private void initializeViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emailAddressEditText = (EditText) findViewById(R.id.input_email);
        lastNameEditText = (EditText) findViewById(R.id.input_first_name);
        firstNameEditText = (EditText) findViewById(R.id.input_last_name);

    }

    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }

}
