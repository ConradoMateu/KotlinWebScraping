package datasource

import domain.Product
import java.io.File

class ProductDAO {

    private val processedProducts = mutableListOf<Product>()

    fun getAll(): List<String> =
            File(javaClass.classLoader.getResource("productos.txt").file).readLines()

    fun getProcessedProducts(): List<Product> =
            processedProducts

    fun addAll(products: List<Product>): List<Product> {
        val newProducts = products.minus(processedProducts)
        processedProducts.addAll(newProducts)
        return processedProducts
    }

    fun addProduct(product: Product): List<Product> {
        processedProducts.add(product)
        return processedProducts
    }

}