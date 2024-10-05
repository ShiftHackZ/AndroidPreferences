# Android Preferences

Android Preferences is a library that provides easy delegates to manage shared preferences as read/write values.

<p>
  <a href="https://jitpack.io/#ShiftHackZ/AndroidPreferences">
    <img src="https://jitpack.io/v/ShiftHackZ/AndroidPreferences.svg" />
  </a>
</p>

## Example

1. Create a class with your variables.

```kotlin
class PreferencesManager(preferences: SharedPreferences) {
    var name: String by preferences.delegates.string()
    var set: Set<String> by preferences.delegates.stringSet()
    var count: Int by preferences.delegates.int()
    var point: Float by preferences.delegates.float()
    var long: Long by preferences.delegates.long()
}
```

2. Instantiate your class.

```kotlin
val key = "preferences_key"
val prefs = context.getSharedPreferences(key, Context.MODE_PRIVATE)
val manager = PreferencesManager(prefs)
```

3. Then you can just get or set the value in preference manager to get or store data in preferences.

```kotlin
println("User name is: ${manager.name}")
manager.name = "John"
println("New user name is: ${manager.name}")
```

## Implementation

```groovy
buildscript {
    repositories {
        maven { setUrl("https://jitpack.io") }
    }
}
```

Then add all or only needed library module dependencies to your module level build gradle:

```
dependencies {
    implementation 'com.github.ShiftHackZ:AndroidPreferences:<VERSION>'
}
```
