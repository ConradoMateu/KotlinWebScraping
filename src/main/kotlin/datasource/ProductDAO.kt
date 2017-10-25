package datasource

import java.io.File

class ProductDAO {

    fun getAll(): List<String> =
            File(javaClass.classLoader.getResource("productos.txt").file).readLines()

}