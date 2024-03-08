<p><img src="/media/icon.png" alt="icon" width="100" align="right"/></p></br>

# Who's Next!?
A simple application that lets you know when it’s time to change the goalkeeper in a football game with friends.  

<p align="center">
  <img src="/media/android.png" alt="android" width="200"/>
  <img src="/media/ios.png" alt="ios" width="200"/>
  <img src="/media/desktop.png" alt="desktop" width="200"/>  
  <img src="/media/wasm.png" alt="wasm" width="600"/>
</p>

## Details

Uses [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/) for sharing the _Data Layer_ between all platforms and [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) to create a cross-platform _Ui Layer_. It also uses SwiftUI to provide an additional iOS _Ui Layer_.  

Supports: `jvm` (android and desktop), `native` (iOS) and `wasm` (web)

### Modules

#### shared

- Model-View-Intent architecture with state emission by Kotlin Flow
- Finite State Machine to validate state transitions
- State restoration (both for UI State and FSM State)
- ViewModel shared by [KMM-ViewModel](https://github.com/rickclephas/KMM-ViewModel)

<img src="/media/fsm.png" alt="fsm" width="412"/> <img src="/media/wn.png" alt="wn" width="200"/> <img src="/media/wnp.png" alt="wnp" width="200"/>

#### shared-ui

- Components
- Screens
- Resources

#### androidApp + iosApp

- Android uses Compose Multiplatform
- iOS uses SwiftUI and Compose Multiplatform

`./gradlew :shared-ui:main:installDebug`  

**Note:** due to current limitations in Compose Multiplatform, resources in multimodule projects are not supported yet. As a result, the `androidApp` implementation is located within the `androidMain` of the `shared-ui` module.

To run `iosApp` open `iosApp/iosApp.xcodeproj` in Xcode and run standard configuration or use [KMM plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio and choose `iosApp` in `run configurations`.

#### desktopApp

`./gradlew :desktopApp:run`

#### browserApp

`./gradlew :shared-ui:wasmJsBrowserDevelopmentRun`  

**Note:** due to current limitations in Compose Multiplatform, resources in multimodule projects are not supported yet. As a result, the `browserApp` implementation is located within the `wasmJsMain` of the `shared-ui` module.

## UI

<p align="center">
  <img src="/media/android.gif" alt="android" width="200"/>&nbsp;&nbsp;
  <img src="/media/ios.gif" alt="ios" width="200"/>&nbsp;&nbsp;
  <img src="/media/desktop.gif" alt="desktop" width="200"/></br></br>
  <img src="/media/wasm.gif" alt="wasm" width="600"/>
</p>

## LICENSE

Copyright (c) 2024-present GuilhE

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy
of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under
the License.
