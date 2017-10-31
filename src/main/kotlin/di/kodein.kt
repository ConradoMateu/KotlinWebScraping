package di

import com.github.salomonbrys.kodein.instance
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : Any, A> kdi(): ReadOnlyProperty<A, T> = object : ReadOnlyProperty<A, T> {
    override fun getValue(thisRef: A, property: KProperty<*>): T =
        Application.kodein.instance<T>()
}

inline fun <reified T : Any, A> kdi(tag: String): ReadOnlyProperty<A, T> = object : ReadOnlyProperty<A, T> {
    override fun getValue(thisRef: A, property: KProperty<*>): T =
            Application.kodein.instance<T>(tag)
}