package usecases

import datasource.ProductDAO
import di.kdi

class GetProducts {

    private val productDAO : ProductDAO by kdi()

    operator fun invoke(): List<String> = productDAO.getAll()

}