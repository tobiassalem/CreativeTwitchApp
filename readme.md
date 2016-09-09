### Summary

A very simple proof of concept application illustrating how to connect to a backend API service and present the data with Android.
The ambition is to use Best Practices like Clean Code and Design Patterns wherever possible. The motivations for this approach are plentiful, which every experienced developer knows, and the balance is with pragmatism.

In this simple example the top 10 Twitch Games based on current viewers are listed. 
Upon selecting one of the games the top 10 streams for that games are listed.

### Possible improvements

The possible improvements to the applications are of course many. The most relevant can easily be the following.

* Implement instrumentation and unit tests. Especially for the JSON deserialization. [must have]
* Convert the project to use Material Design best practices (e.g. RecycleView instead of ListView).
* Implement support for more devices and screen resolutions (e.g. use Fragments and more image dpi versions).
* Implement smarter caching for the game and stream images (at the moment WebView's out-of-the-box caching is used).
* Implement an applications menu and Settings view where the number of top games and streams respectively can be selected.
* Implement Twitch user authentication and thereby allow viewing and editing of favorite games.
* Implement viewing of a selected stream.

There are of course many network libraries to choose from. I here selected Retrofit for it's elegance in defining and using backend endpoints.
Concretely compared to RoboSpice which I've used before, Retrofit is really superior.

### Known issues

* The WebView component has some performance issues when used in a ListView.
* All design specifications (sizes, colors and related) are not yet extracted to styles.xml
* The applications is very simple in it's current form, as the possible improvements clearly indicate. 

### Installation

Import the Gradle aware project into Android Studio and run with your favorite emulator or device.

### References

* Android SDK (min version 15, target version 23) - @See https://developer.android.com/
* Gradle build tool - @See https://gradle.org/
* Retrofit network library. - @See http://square.github.io/retrofit/
* Clean Code by Robert C. Marting - @See https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882