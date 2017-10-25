package usecases

import base.UseCase
import datasource.BrandDAO
import di.kdi

class GetBrands : UseCase<List<String>> {

    private val brandsDAO : BrandDAO by kdi()

    override fun execute(): List<String> = brandsDAO.getAll()
}