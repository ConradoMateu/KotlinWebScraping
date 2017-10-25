package usecases

import base.UseCase
import datasource.ProductDAO
import di.kdi

class GetProducts : UseCase<List<String>> {

    private val productDAO : ProductDAO by kdi()

    override fun execute(): List<String> = productDAO.getAll()

}