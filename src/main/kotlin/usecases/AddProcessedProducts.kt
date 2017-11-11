package usecases

import datasource.ProductDAO
import di.kdi
import domain.Product

class AddProcessedProducts {

    private val dao: ProductDAO by kdi()

    operator fun invoke(products: List<Product>): List<Product> {
        if (dao.getProcessedProducts().isEmpty()) {
            return dao.addAll(products)
        }

        val processedProducts: MutableCollection<Product> = mutableListOf()
        val alreadyAddedProducts = dao.getProcessedProducts().toMutableList()
        val mutableProducts = products.toMutableList()
        val disposableProducts = mutableListOf<Product>()


        alreadyAddedProducts.forEach { product ->
            val currentProduct = mutableProducts.find { it.identifier == product.identifier }
            if (currentProduct != null) {
                val alreadyAddedProductPrices = product.price.toList()
                val currentProductPrices = currentProduct.price.toList()
                val copy = currentProduct.copy(price = currentProductPrices.union(alreadyAddedProductPrices).toMap())
                processedProducts.add(copy)
                mutableProducts.remove(currentProduct)
                disposableProducts.add(product)
            }
        }

        disposableProducts.forEach { alreadyAddedProducts.remove(it) }

        processedProducts.addAll(alreadyAddedProducts)
        processedProducts.addAll(mutableProducts)

        val result = processedProducts.toList()
        return dao.addAll(result)
    }

}