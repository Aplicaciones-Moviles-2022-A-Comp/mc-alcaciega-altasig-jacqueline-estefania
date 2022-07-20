import java.io.*
import java.util.*
import kotlin.collections.ArrayList


fun Deber001(){
    var universo: Universo
    var planeta: Planeta
    Universo.modificarUniverso()
    Planeta.modificarPlaneta()
    println("----------------------------------------------")
    println("-------------ALCACIEGA JACQUELINE ------------")
    println("-------------------DEBER 01-------------------")
    println("--------------APLICACIONES MOVILES------------")

    var select = 2
    do {
        println("Elige opción:\n" +
                "---------------------------\n" +
                "1. Crear Universo \n" +
                "2. Listar  Universo\n"+
                "3. Eliminar Universo \n" +
                "4. Modificar Universo\n" +
                "5. Crear Planeta\n" +
                "0. Salir\n" +
                "Ingrese una opción:\n"+
                "---------------------------\n")
        val selected = readLine()!!.toInt()
        when (selected){
            1 -> {

                universo = Universo(cargarString("Nombre del Universo "),cargarString("Tamano"),cargarString("componentes"), cargarString("Color"), cargarDouble("numeroPlanetas"))
                Universo.crearUniverso(universo)
                Universo.modificarUniverso()
            }
            2->{
                Universo.listarUniverso()
            }
            3 ->{
                Universo.listarUniverso()
                println("Ingrese el indice del Universo para eliminar:")
                val valor = readLine()!!.toInt()
                Universo.eliminarUniverso(valor)
                Universo.modificarUniverso()
            }
            4 ->{
                Universo.listarUniverso()
                println("Ingrese el indice del Universo para modificar:")
                val valor = readLine()!!.toInt()
                Universo.updateUniverso(valor)
                Universo.modificarUniverso()
            }
            5->{
                var select1 = 1
                do{
                    println("Elige opción:\n" +
                            "---------------------------\n"+
                            "0. Atras\n" +
                            "1. Crear Planetas\n" +
                            "2. Listar  Planetas\n"+
                            "3. Eliminar Planetas\n" +
                            "4. Modificar Planetas\n" +
                            "0. Atras\n" +
                            "Ingrese la opción:"+
                            "---------------------------\n")
                    val selected1 = readLine()!!.toInt()
                    when(selected1){
                        1->{
                            Universo.listarUniverso()
                            println("Ingrese el indice del Universo para Crear los Planeta:")
                            val indice = readLine()!!.toInt()

                            planeta = Planeta (cargarString("Planeta 1"), cargarString("Planeta 2"), cargarString("Planeta 3"), cargarString("Planeta 4"), cargarString("Planeta 5")
                                , Universo.listaUniverso[indice].nombreUniverso, Universo.listaUniverso[indice].tamanoUniverso, Universo.listaUniverso[indice].componentesUniverso
                                , Universo.listaUniverso[indice].ColorUniverso, Universo.listaUniverso[indice].numeroPlanetasUniverso)
                            Planeta.crearPlaneta(planeta)
                            Planeta.modificarPlaneta()
                        }
                        2->{
                            Planeta.listarPlaneta()
                        }
                        3 ->{
                            Planeta.listarPlaneta()
                            println("Ingrese el indice del Planeta para eliminar:")
                            val valor = readLine()!!.toInt()
                            Planeta.eliminarPlaneta(valor)
                            Planeta.modificarPlaneta()
                        }
                        4 ->{
                            Planeta.listarPlaneta()
                            println("Ingrese el indice del Planeta para modificar:")
                            val valor = readLine()!!.toInt()
                            Planeta.updatePlaneta(valor)
                            Planeta.modificarPlaneta()
                        }
                        0->{
                            println("¿Deseas regresar al otro nodo? S/N ")
                            println("1 =  Si 2 = No ")
                            val seleccionado = readLine()!!.toInt()
                            if ( seleccionado == 1)
                                select1 = 0
                        }
                        else ->{
                            println("Número no reconocido")
                        }
                    }
                }while(select1 != 0)
            }
            0->{
                println("¿Deseas salir? S/N ")
                println(" 1 =  Si  2 = No ")
                val seleccionado = readLine()!!.toInt()
                if ( seleccionado == 1)
                    select = 0
            }
            else ->{
                println("Número no reconocido")
            }

        }


    }while (select != 0)

}


fun cargarString(titulo: String):  String{
    print("Ingrese  ${titulo}:")
    val valor = readLine()!!.toString()
    return valor
}

fun cargarDouble(titulo: String):  Double{
    print("Ingrese  ${titulo}:")
    val valor = readLine()!!.toDouble()
    return valor
}

open class Universo(
    var nombreUniverso: String,
    var tamanoUniverso: String,
    var componentesUniverso: String,
    var ColorUniverso: String,
    var numeroPlanetasUniverso: Double
) {

    companion object {
        val listaUniverso: ArrayList<Universo> = arrayListOf()
        fun crearUniverso(UniversoNueva:Universo){
            listaUniverso.add(UniversoNueva)
            println("Universo agregada!!")
        }
        fun listarUniverso(){
            listaUniverso
                .forEachIndexed() { index: Int, valorActual: Universo ->
                    println("Universo[${index}]: [Nombre Universo: ${valorActual.nombreUniverso} , Tamano: ${valorActual.tamanoUniverso} " +
                            ", Componentes: ${valorActual.componentesUniverso}, Color: ${valorActual.ColorUniverso}" +
                            ",numeroPlanetas: ${valorActual.numeroPlanetasUniverso}]")
                }
            if(listaUniverso.size == 0)
                println("Esta vacia!!")
        }
        fun eliminarUniverso(index: Int){
            if (listaUniverso.size > 0){
                listaUniverso.removeAt(index)
                println("Eliminado!!")
            }
        }
        fun updateUniverso(i: Int){
            listaUniverso.mapIndexed { index, Universo ->
                if(index == i){
                    Universo.nombreUniverso = cargarString("Nombre del Universo ")
                    Universo.tamanoUniverso = cargarString("Tamano del Universo ")
                    Universo.componentesUniverso = cargarString("Componentes del Universo ")
                    Universo.ColorUniverso = cargarString("Color del Universo ")
                    Universo.numeroPlanetasUniverso = cargarDouble("numeroPlanetas del Universo ")
                }
                return@mapIndexed Universo
            }
        }
        fun modificarUniverso() {
            val ruta = "src/main/kotlin/universo.txt"
            try {
                FileWriter(ruta, false).use { fw ->
                    BufferedWriter(fw).use { bw ->
                        PrintWriter(bw).use { out ->
                            listaUniverso.forEachIndexed() { index, valorActual ->  out.println("Universo[${index}]: [Nombre Universo: ${valorActual.nombreUniverso} , Tamano: ${valorActual.tamanoUniverso} " +
                                    ", Componentes: ${valorActual.componentesUniverso}, Color: ${valorActual.ColorUniverso} " +
                                    " ,numeroPlanetas: ${valorActual.numeroPlanetasUniverso}]") }
                        }
                    }
                }
            } catch (e: IOException) {
                //exception handling left as an exercise for the reader
            }
        }
    }


}
class Planeta(
    var nombrePlaneta1: String,
    var nombrePlaneta2: String,
    var nombrePlaneta3: String,
    var nombrePlaneta4: String,
    var nombrePlaneta5: String, nombreUniverso: String, tamanoUniverso: String, componentesUniverso: String, ColorUniverso: String,
    numeroPlanetasUniverso: Double,
):Universo(nombreUniverso, tamanoUniverso, componentesUniverso, ColorUniverso, numeroPlanetasUniverso){
    companion object {
        val listaPlaneta: ArrayList<Planeta> = arrayListOf()
        fun crearPlaneta(PlanetaNueva:Planeta){
            listaPlaneta.add(PlanetaNueva)
            println("Planeta agregada!!")
        }
        fun listarPlaneta(){
            listaPlaneta
                .forEachIndexed() { index: Int, valorActual: Planeta ->
                    println("Planeta[${index}]: [Planeta 1 ${valorActual.nombrePlaneta1} , Planeta 2:${valorActual.nombrePlaneta2} " +
                            ",Planeta 3: ${valorActual.nombrePlaneta3}, Planeta 4: ${valorActual.nombrePlaneta4}" +
                            ", Planeta 5 ${valorActual.nombrePlaneta5}] <- Universo { Nombre de la Universo: ${valorActual.nombreUniverso} , Tamano: ${valorActual.tamanoUniverso} " +
                            "Componentes: ${valorActual.componentesUniverso}, Color: ${valorActual.ColorUniverso}" +
                            ", numeroPlanetas: ${valorActual.numeroPlanetasUniverso}} ")
                }
            if(listaPlaneta.size == 0)
                println("Esta vacia!!")
        }
        fun eliminarPlaneta(index: Int){
            if (listaUniverso.size > 0){
                listaPlaneta.removeAt(index)
                println("Eliminado!!")
            }
        }
        fun updatePlaneta(i: Int){
            listaPlaneta.mapIndexed { index, Planeta ->
                if(index == i){
                    Universo.listarUniverso()
                    println("Ingrese el indice las espcies para crear la Planeta:")
                    val indice = readLine()!!.toInt()
                    Planeta.nombrePlaneta1 = cargarString("Planeta 1")
                    Planeta.nombrePlaneta2 = cargarString("Planeta 2")
                    Planeta.nombrePlaneta3 = cargarString("Planeta 3")
                    Planeta.nombrePlaneta4 = cargarString("Planeta 4")
                    Planeta.nombrePlaneta5 = cargarString("Planeta 5")
                    Planeta.nombreUniverso = Universo.listaUniverso[indice].nombreUniverso
                    Planeta.tamanoUniverso = Universo.listaUniverso[indice].tamanoUniverso
                    Planeta.componentesUniverso = Universo.listaUniverso[indice].componentesUniverso
                    Planeta.ColorUniverso = Universo.listaUniverso[indice].ColorUniverso
                    Planeta.numeroPlanetasUniverso = Universo.listaUniverso[indice].numeroPlanetasUniverso
                }
                return@mapIndexed Planeta
            }
        }
        fun modificarPlaneta() {
            val ruta = "src/main/kotlin/Planeta.txt"
            try {
                FileWriter(ruta, false).use { fw ->
                    BufferedWriter(fw).use { bw ->
                        PrintWriter(bw).use { out ->
                            listaPlaneta.forEachIndexed() { index, valorActual ->  out.println("Planeta[${index}]: [Planeta 1 ${valorActual.nombrePlaneta1} , Planeta 2:${valorActual.nombrePlaneta2} " +
                                    ",Planeta 3: ${valorActual.nombrePlaneta3}, Planeta 4: ${valorActual.nombrePlaneta4}" +
                                    ", Planeta 5 ${valorActual.nombrePlaneta5}] <- Universo { Nombre del Universo : ${valorActual.nombreUniverso} , Tamano: ${valorActual.tamanoUniverso} " +
                                    "componentes: ${valorActual.componentesUniverso}, Color: ${valorActual.ColorUniverso}" +
                                    ", numeroPlanetas: ${valorActual.numeroPlanetasUniverso}} ") }
                        }
                    }
                }
            } catch (e: IOException) {
                //exception handling left as an exercise for the reader
            }
        }
    }

}

