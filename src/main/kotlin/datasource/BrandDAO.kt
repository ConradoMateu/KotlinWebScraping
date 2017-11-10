package datasource

import tornadofx.Component
import tornadofx.ScopedInstance
import java.io.File

class BrandDAO {

    fun getAll(): List<String> =
            javaClass.classLoader.getResourceAsStream("marcas.txt").reader().readLines()

}