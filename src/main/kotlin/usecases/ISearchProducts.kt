package usecases

import datasource.Stores.StoreRepository

interface ISearchProducts {
    val webDriver: StoreRepository
}