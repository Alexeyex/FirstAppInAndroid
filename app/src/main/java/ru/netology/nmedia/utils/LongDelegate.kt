package ru.netology.nmedia.utils

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object LongDelegate : ReadWriteProperty<Bundle, Long?> {
    override fun getValue(thisRef: Bundle, property: KProperty<*>): Long? = thisRef.getSerializable(property.name) as? Long
    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Long?) {
        thisRef.putSerializable(property.name, value)
    }
}