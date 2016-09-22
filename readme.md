### Overview

A very simple proof of concept application illustrating how to connect to a backend service and present the data with Android.
The ambition is to use Best Practices like Clean Code and Design Patterns wherever possible. The motivations for this approach are plentiful, which every experienced developer knows, and the balance is with pragmatism.

The backend service in this case is the Twitch API. Twitch is the world's leading social video platform and community for gamers, video game culture, and the creative arts.

In this simple app the top five Twitch Games based on the current number of viewers are listed. 
Upon selecting one of the games the top ten streams for that games are listed, also based on the number of viewers.

### Possible improvements

The possible improvements to the application are of course many. The most relevant can easily be the following.

* Implement instrumentation and unit tests. Especially for the JSON deserialization. [must have]
* Implement a version of the Android MVP (Model View Presenter) pattern [must have]
* Convert the project to use Material Design best practices (e.g. RecycleView instead of ListView). [nice to have]
* Implement support for more devices and screen resolutions (e.g. use Fragments and more image dpi versions). [must have]
* Implement an applications menu and Settings view where the number of top games and streams respectively can be selected. [nice to have]
* Implement Twitch user authentication and thereby allow viewing and editing of favorite games. [nice to have]
* Implement gesture detection for manually updating the respective top lists [nice to have]
* Implement viewing of a selected stream. [nice to have]

There are of course many network libraries to choose from. I here selected Retrofit for its elegance in defining and using backend endpoints.
Concretely compared to RoboSpice which I've used alot before, Retrofit truly is superior.

### Implemented improvements

* Optimized network image loading.
* Registered the app at Twitch and now sending clientId for each request as per the documentation (required from September 2016).

### Known issues

* The application is very simple in its current form, as the possible improvements clearly indicate. 

### Installation

Import the Gradle aware project into Android Studio and run with your favorite emulator or device.

### References

* Android SDK (see build.gradle for version details) - @See https://developer.android.com/
* Gradle build tool - @See https://gradle.org/
* Retrofit network library. - @See http://square.github.io/retrofit/
* Android MVP pattern - @See http://antonioleiva.com/mvp-android/
* Clean Code by Robert C. Martin - @See https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882
