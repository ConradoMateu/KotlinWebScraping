package usecases

import datasource.ProductDAO
import di.kdi
import domain.Product

class AddProcessedProducts {

    private val dao: ProductDAO by kdi()

    operator fun invoke(products: List<Product>): List<Product> =
            dao.addAll(products)
}