package com.shifthackz.android.core.preferences

import android.content.SharedPreferences

interface PreferencesManager {
    var name: String
    var set: Set<String>
    var count: Int
    var point: Float
    var long: Long
}

class PreferencesManagerExample(
    preferences: SharedPreferences,
) : PreferencesManager {

    override var name: String by preferences.delegates.string()

    override var set: Set<String> by preferences.delegates.stringSet()

    override var count: Int by preferences.delegates.int()

    override var point: Float by preferences.delegates.float()

    override var long: Long by preferences.delegates.long()

}
