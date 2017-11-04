package domain

import java.text.NumberFormat
import java.util.*
import java.util.Collections.replaceAll

data class Product(val name: String,
                   val brand: String,
                   val price: Double,
                   val store: String)

fun String.parseDouble(): Double {
    val format = NumberFormat.getInstance(Locale.FRANCE)
    val number = format.parse(this.replace(".", ""))
    return number.toDouble()
}