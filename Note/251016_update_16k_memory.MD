16KB 기기 지원

https://developer.android.com/guide/practices/page-sizes#build


1. [Update the packaging of your shared libraries](https://developer.android.com/guide/practices/page-sizes#update-packaging)

```
Tip: If you update your tools to the latest versions (AGP version 8.5.1 or higher and NDK version r28 or higher) and 
use 16 KB-compatible prebuilt dependencies, 
then your app is 16 KB compatible by default and 
you can skip to the step for fixing code and resolving runtime issues.
```

current apg version
```
agp = "8.2.0"
```


[Test your app in a 16 KB environment](https://developer.android.com/guide/practices/page-sizes#test)

[1. Set up the Android 15 SDK.](https://developer.android.com/about/versions/15/setup-sdk)
```
android {
    compileSdk = 35
}
```
2. Set up one of the following testing environments:
[Set up the Android Emulator with a 16 KB–based, Android 15 system image](https://developer.android.com/guide/practices/page-sizes#16kb-emulator)

In Android Studio, click Tools > SDK Manager.
Google APIs Experimental 16 KB Page Size ARM 64 v8a System Image
Google APIs Experimental 16 KB Page Size Intel x86_64 Atom System Image

