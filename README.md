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

#### Reactive Results

#### Chain Transformation

## Demo
You can see a full demo of the library in the [sample](https://github.com/mohamad-amin/RxActivityResults/tree/master/sample) module.

## License
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
