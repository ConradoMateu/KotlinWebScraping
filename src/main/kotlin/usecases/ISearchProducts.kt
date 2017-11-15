package usecases

import datasource.BrandDAO
import datasource.Stores.StoreRepository
import di.kdi
import domain.Product
import org.apache.commons.lang3.StringUtils

abstract class ISearchProducts {
    abstract val webDriver: StoreRepository
    protected val brandsDAO : BrandDAO by kdi()
    private val buzzwords: List<String>

    init {
        buzzwords = listOf(
                "cafetera", "cafe", "negro", "negra", "rojo", "roja", "amarillo", "amarilla",
                "de", "para", "capsulas", "espresso", "automatica", "filtro", "molinillo", "multibebida",
                "electrica", "expresso", "vivy", "grind", "goteo", "brew", "superautomatica", "n",
                "senseo", "intense", "coffee", "maker", "blanco", "blanca", "con", "la", "jarra",
                "dolce", "gusto", "f", "nescafe", "italiana", "hidro-presion", "russell", "hobbs", "super"
        ).union(brandsDAO.getAll().map(String::toLowerCase)).toList()
    }

    abstract operator fun invoke(productName: String, brand: String? = null, page: Int = 1): List<Product>

    fun extractBuzzWords(productName: String): String? {
        val productName = StringUtils.stripAccents(productName.toLowerCase())
        return productName.split(" ").filterNot { buzzwords.contains(it) }.firstOrNull()
    }
}