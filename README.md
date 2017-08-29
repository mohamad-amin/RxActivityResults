![Travis-ci](https://api.travis-ci.org/mohamad-amin/RxActivityResults.svg)

# RxActivityResults
This library uses the power of **RxJava** to wrap an `Observable` android `Activity#onActivityResult()` method 
so you can easily request something from other activities and have the result right there in your 
observabale's `subscribe()` method.

#### Main Benifits

- **Don't Break The Chain**: Prevents you to split your code between the permission request and the result handling. Currently without this library you have to request in one place and handle the result in `Activity#onActivityResult()`.
- **Reactive**: All what RX provides about transformation, filter, chaining...

If you have any issues or need more features, you can submit an issue in the 
[issue tracker](https://github.com/mohamad-amin/RxActivityResults/issues) or make a 
[pull request](https://github.com/mohamad-amin/RxActivityResults/pulls).

  * [Importing](#importing)
  * [Usage](#usage)
      * [Reactive Results](#reactive-results)
      * [Chain Transformation](#chain-transformation)
  * [Demo](#demo)
  * [Credits](#credits)
  * [Licence](#licence)
  
  
![Demo](https://github.com/mohamad-amin/RxActivityResults/blob/master/art/demo.gif)

## Importing
Add this line to your module's `build.gradle` file:
```groovy
dependencies {
    compile 'com.mohamadamin.rxactivityresults:rxactivityresults:0.1'
}
```

## Usage
First you need to create an instance of `RxActivityResults` in your `Activity`:
```java
RxActivityResults rxActivityResults;

@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    rxActivityResults = new RxActivityResults(this);
    ...
}
```
Then you can use this instance to start an activity for result and have it's result in your observer like this:
```java
Intent intent = new Intent(this, LoginActivity.class);
rxActivityResults.start(loginIntent)
                .subscribe(activityResult -> {
                    // Checking if the result's resultCode is Activity.RESULT_OK
                    if (activityResult.isOk()) {
                        Intent responseData = activityResult.getData();
                        // Do some other things with the response data
                   }
                });
      
```
#### Reactive Results
You can use `RxActivityResults#composer(Intent intent)` method to have an `ObservableTransformer` to chain your observables together and avoid from breaking the reactive chain:

(**Note**: we used [RxBinding](https://github.com/JakeWharton/RxBinding) library to turn view clicks into an observable)
```java
Disposable disposable = RxView.clicks(loginButton)
        // Fire an intent when the user clicks on the button
        .compose(rxActivityResults.composer(getLoginIntent()))
        // Filter the activity results which their resultCode is Activity.RESULT_OK
        .filter(activityResult -> activityResult.isOk())
        // Extract the data intent of the activity result
        .map(ActivityResult::getData)
        .subscribe(intent -> {

            String firstName = intent.getStringExtra(LoginActivity.FIRST_NAME);
            String lastName = intent.getStringExtra(LoginActivity.LAST_NAME);
            String emailAddress = intent.getStringExtra(LoginActivity.EMAIL_ADDRESS);

            String result = getString(R.string.first_name) + ": " + firstName + "\n"
                    + getString(R.string.last_name) + ": " + lastName + "\n"
                    + getString(R.string.email_address) + ": " + emailAddress + "\n";

            loginInfoText.setText(result);

        });

disposables.add(disposable);
```
#### Chain Transformation
You can use `RxActivityResults#ensureOkResult(Intent intent)` method to have an `ObservableTransformer` to chain your observables together and avoid from breaking the reactive chain:
```java
Disposable disposable = someAnotherChainOfObservables
        // Fire an intent when the user clicks on the button
        .compose(rxActivityResults.ensureOkResult(getIntent()))
        .switchMap(okResult -> {
            if (okResult) {
                return someAnotherObservable();
            } else {
                return Observable.error(new RuntimeException("Intent didn't return RESULT_OK"));
            }
        })
        .flatMap(...)
        ...

disposables.add(disposable);
```
## Demo
You can see a full demo of the library in the [sample](https://github.com/mohamad-amin/RxActivityResults/tree/master/sample) module.

## Credits
This library was inspired by [RxPermissions](https://github.com/tbruyelle/RxPermissions), so great thanks to [@tbruyelle](https://github.com/tbruyelle) for his great contribution.

## Licence
```
Copyright 2017 Mohamad Amin Mohamadi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
