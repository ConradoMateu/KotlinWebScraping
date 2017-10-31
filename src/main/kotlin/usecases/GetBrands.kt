package usecases

import datasource.BrandDAO
import di.kdi

class GetBrands {

    private val brandsDAO : BrandDAO by kdi()

    operator fun invoke(): List<String> = brandsDAO.getAll()
}