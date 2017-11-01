package usecases

import datasource.ProductDAO
import di.kdi
import domain.Product

class GetProcessedProducts {

    private val dao: ProductDAO by kdi()

    operator fun invoke(): List<Product> =
        dao.getProcessedProducts()
}