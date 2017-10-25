package datasource

import tornadofx.Component
import tornadofx.ScopedInstance
import java.io.File

class BrandDAO {

    fun getAll(): List<String> =
            File(javaClass.classLoader.getResource("marcas.txt").file).readLines()

}