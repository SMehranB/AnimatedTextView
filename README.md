# Animated Text View [![](https://jitpack.io/v/SMehranB/AnimatedTextView.svg)](https://jitpack.io/#SMehranB/AnimatedTextView)

## A text view with the ability to animate your values
 
## Features!

 •	 Set a fixed prefix
 
 •	 Set a fixed suffix
 
 •	 Adjust animation duration
 
 •	 An easy-to-use animateTo() function
 

## Screen recording
 
 <img src="./screen_recording.png" height="720">
 
# Install

 ## Gradle
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```
dependencies {
	 implementation 'com.github.SMehranB:AnimatedTextView:1.0.0'
}
```
## Maven
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```
<dependency>
	<groupId>com.github.SMehranB</groupId>
	<artifactId>AnimatedTextView</artifactId>
	<version>1.0.0</version>
</dependency>
 ```
# Use
 
## XML
```xml
<com.smb.animatedtextview.AnimatedTextView
    android:id="@+id/txtPriceOne"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginStart="8dp"
    android:layout_marginTop="16dp"
    android:layout_marginEnd="8dp"
    android:background="@drawable/border"
    android:paddingVertical="8dp"
    android:paddingStart="16dp"
    android:text="123,456"
    android:textColor="@color/white"
    android:textSize="24dp"
    app:atv_animationDuration="1500"
    app:atv_prefixToExclude="Price: "
    app:atv_suffixToExclude=" CAD"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
 ```

## Kotlin

```kotlin
txtPriceOne.setPrefixSuffix("Price: ", " CAD")
txtPriceOne.setText("123,456")
txtPriceOne.animateTo("731,984,625")
```

## 📄 License
```text
MIT License

Copyright (c) 2021 Seyed Mehran Behbahani

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
