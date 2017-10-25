package base

import tornadofx.Component
import tornadofx.ScopedInstance

interface DAO<out T> {
    fun getAll(): List<T>
}