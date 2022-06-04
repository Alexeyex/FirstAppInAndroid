package ru.netology.nmedia.utils

import android.os.Bundle
import android.os.Parcelable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ParcelableDelegate<T: Parcelable> : ReadWriteProperty<Bundle, T?> {
    override fun getValue(thisRef: Bundle, property: KProperty<*>): T? = thisRef.getParcelable(property.name)
    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: T?) {
        thisRef.putParcelable(property.name, value)
    }
}