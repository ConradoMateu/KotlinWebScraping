package domain

import java.text.NumberFormat
import java.util.*

data class Product(val name: String,
                   val brand: String,
                   val fnac: Double = -1.0,
                   val corteIngles: Double = -1.0,
                   val amazon: Double = -1.0)

fun String.parseDouble(): Double {
    val format = NumberFormat.getInstance(Locale.FRANCE)
    val number = format.parse(this.replace(".", ""))
    return number.toDouble()
}