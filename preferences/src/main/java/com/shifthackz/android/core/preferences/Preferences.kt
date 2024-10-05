package com.shifthackz.android.core.preferences

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

val SharedPreferences.delegates: PreferencesDelegates
    get() = PreferencesDelegates(this)

class PreferencesDelegates(
    private val preferences: SharedPreferences,
) {
    fun boolean(
        default: Boolean = false,
        key: String? = null,
        onChanged: (Boolean) -> Unit = {},
    ): ReadWriteProperty<Any, Boolean> = create(
        default = default,
        key = key,
        getter = preferences::getBoolean,
        setter = preferences.edit()::putBoolean,
        onChanged = onChanged,
    )

    fun int(
        default: Int = 0,
        key: String? = null,
        onChanged: (Int) -> Unit = {},
    ): ReadWriteProperty<Any, Int> = create(
        default = default,
        key = key,
        getter = preferences::getInt,
        setter = preferences.edit()::putInt,
        onChanged = onChanged,
    )

    fun float(
        default: Float = 0f,
        key: String? = null,
        onChanged: (Float) -> Unit = {},
    ): ReadWriteProperty<Any, Float> = create(
        default = default,
        key = key,
        getter = preferences::getFloat,
        setter = preferences.edit()::putFloat,
        onChanged = onChanged,
    )

    fun long(
        default: Long = 0L,
        key: String? = null,
        onChanged: (Long) -> Unit = {},
    ): ReadWriteProperty<Any, Long> = create(
        default = default,
        key = key,
        getter = preferences::getLong,
        setter = preferences.edit()::putLong,
        onChanged = onChanged,
    )

    fun string(
        default: String = "",
        key: String? = null,
        onChanged: (String) -> Unit = {},
    ): ReadWriteProperty<Any, String> = create(
        default = default,
        key = key,
        getter = { k, d -> preferences.getString(k, d) as String },
        setter = preferences.edit()::putString,
        onChanged = onChanged,
    )

    fun stringSet(
        default: Set<String> = emptySet(),
        key: String? = null,
        onChanged: (Set<String>) -> Unit = {},
    ): ReadWriteProperty<Any, Set<String>> = create(
        default = default,
        key = key,
        getter = { k, d -> preferences.getStringSet(k, d) as Set<String> },
        setter = preferences.edit()::putStringSet,
        onChanged = onChanged,
    )

    private fun <T> create(
        default: T,
        key: String? = null,
        getter: (key: String, default: T) -> T,
        setter: (key: String, value: T) -> SharedPreferences.Editor,
        onChanged: (T) -> Unit = {},
    ) = object : ReadWriteProperty<Any, T> {

        private fun key(property: KProperty<*>) = key ?: property.name

        override fun getValue(thisRef: Any, property: KProperty<*>): T =
            getter(key(property), default)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            setter(key(property), value).apply()
            onChanged(value)
        }
    }
}
