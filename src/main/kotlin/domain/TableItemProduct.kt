package domain

data class TableItemProduct(val name: String,
                            val brand: String,
                            val identifier: String?,
                            val amazon: Double = -1.0,
                            val fnac: Double = -1.0,
                            val corteIngles: Double = -1.0)

fun Product.mapToTableItem(): TableItemProduct =
        TableItemProduct(
                this.name,
                this.brand,
                this.identifier,
                this.price["Amazon"] ?: -1.0,
                this.price["Fnac"] ?: -1.0,
                this.price["CorteIngles"] ?: -1.0
        )