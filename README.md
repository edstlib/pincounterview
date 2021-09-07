# PINCounterView

![PinCounterView](https://i.ibb.co/R05d6NJ/pincounterview.jpg)
![PinCounterView](https://i.ibb.co/2qW4FCj/pin3.jpg)

## Setup
### Gradle

Add this to your project level `build.gradle`:
```groovy
allprojects {
 repositories {
    maven { url "https://jitpack.io" }
 }
}
```
Add this to your app `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.edtslib:pincounterview:latest'
}
```
# Usage

The PINCounterView is very easy to use. Just add it to your layout like any other view.
##### Via XML

Here's a basic implementation.

```xml
    <id.co.edtslib.pincounterview.PINCounterView
        android:id="@+id/pinCounterView"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"

        app:pinCounterText="@string/send_code_counter"
        app:pinCounterInterval="180"
        app:pinCounterFormat="text"
        app:pinCounterHour="jam"
        app:pinCounterMinute="menit"
        app:pinCounterSecond="detik"
        
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```

### Attributes information

##### _app:pinCounterText_
[string]: text of counter, must contains %s, which will replace with remain time, for example
```xml
<string name="send_code_counter"><![CDATA[Kirim ulang dapat dilakukan setelah <b>%s</b>.]]></string>
```

##### _app:pinCounterInterval_
[integer]: interval time, in second

##### _app:pinCounterFormat_
[enum]: clock: format remain hh:mm:ss, text: format remain time hour [hour lang] minute [minute lang] second psecond lang]

##### _app:pinCounterHour_
[string]: hour wording, use if pinCounterHour = text

##### _app:pinCounterMinuter_
[string]: minute wording, use if pinCounterHour = text

##### _app:pinCounterHour_
[string]: hour wording, use if pinCounterHour = text

### Method for navigation actions on the PINCounterView
You can start count with call start method

```kotlin
    fun start() 
```

### Listening for PINCounterView

You can set a listener to be notified when remain time is up. An example is shown below.

```kotlin
        val pinCounterView = findViewById<PINCounterView>(R.id.pinCounterView)
        pinCounterView.delegate = object : PINCounterDelegate {
            override fun onExpired() {
                Toast.makeText(this@MainActivity, "Hi, counter is expired", Toast.LENGTH_SHORT).show()
                
            }
        }
```





