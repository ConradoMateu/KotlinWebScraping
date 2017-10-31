package di

import base.UseCase
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.with
import tornadofx.Component
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : Any, A> kdi(): ReadOnlyProperty<A, T> = object : ReadOnlyProperty<A, T> {
    override fun getValue(thisRef: A, property: KProperty<*>): T =
        Application.kodein.instance<T>()
}