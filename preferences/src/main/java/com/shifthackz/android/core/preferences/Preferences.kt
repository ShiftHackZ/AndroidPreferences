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

    fun <T> complexBoolean(
        default: T,
        serialize: (T) -> Boolean,
        deserialize: (Boolean) -> T,
        key: String? = null,
        onChanged: (T) -> Unit = {},
    ): ReadWriteProperty<Any, T> = create(
        default = default,
        key = key,
        getter = { k, d -> deserialize(preferences.getBoolean(k, serialize(d)))},
        setter = { k, v -> preferences.edit().putBoolean(k, serialize(v)) },
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

    fun <T> complexInt(
        default: T,
        serialize: (T) -> Int,
        deserialize: (Int) -> T,
        key: String? = null,
        onChanged: (T) -> Unit = {},
    ): ReadWriteProperty<Any, T> = create(
        default = default,
        key = key,
        getter = { k, d -> deserialize(preferences.getInt(k, serialize(d)))},
        setter = { k, v -> preferences.edit().putInt(k, serialize(v)) },
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

    fun <T> complexFloat(
        default: T,
        serialize: (T) -> Float,
        deserialize: (Float) -> T,
        key: String? = null,
        onChanged: (T) -> Unit = {},
    ): ReadWriteProperty<Any, T> = create(
        default = default,
        key = key,
        getter = { k, d -> deserialize(preferences.getFloat(k, serialize(d)))},
        setter = { k, v -> preferences.edit().putFloat(k, serialize(v)) },
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

    fun <T> complexLong(
        default: T,
        serialize: (T) -> Long,
        deserialize: (Long) -> T,
        key: String? = null,
        onChanged: (T) -> Unit = {},
    ): ReadWriteProperty<Any, T> = create(
        default = default,
        key = key,
        getter = { k, d -> deserialize(preferences.getLong(k, serialize(d)))},
        setter = { k, v -> preferences.edit().putLong(k, serialize(v)) },
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

    fun <T> complexString(
        default: T,
        serialize: (T) -> String,
        deserialize: (String) -> T,
        key: String? = null,
        onChanged: (T) -> Unit = {},
    ): ReadWriteProperty<Any, T> = create(
        default = default,
        key = key,
        getter = { k, d -> deserialize(preferences.getString(k, serialize(d)) as String)},
        setter = { k, v -> preferences.edit().putString(k, serialize(v)) },
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

    fun <T> complexStringSet(
        default: T,
        serialize: (T) -> Set<String>,
        deserialize: (Set<String>) -> T,
        key: String? = null,
        onChanged: (T) -> Unit = {},
    ): ReadWriteProperty<Any, T> = create(
        default = default,
        key = key,
        getter = { k, d -> deserialize(preferences.getStringSet(k, serialize(d)) as Set<String>)},
        setter = { k, v -> preferences.edit().putStringSet(k, serialize(v)) },
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
