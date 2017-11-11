package domain

import java.text.NumberFormat
import java.util.*

data class Product(val name: String,
                   val brand: String,
                   val price: Map<String, Double>,
                   val identifier: String? = null)

fun String.parseDouble(): Double {
    val format = NumberFormat.getInstance(Locale.FRANCE)
    val number = format.parse(this.replace(".", ""))
    return number.toDouble()
}