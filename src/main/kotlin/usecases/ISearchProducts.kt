package usecases

import datasource.Stores.StoreRepository
import domain.Product

interface ISearchProducts {
    val webDriver: StoreRepository

    operator fun invoke(productName: String, brand: String? = null, page: Int = 1): List<Product>
}