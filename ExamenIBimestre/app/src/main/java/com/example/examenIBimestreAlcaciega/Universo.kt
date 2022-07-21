package com.example.examenIBimestreAlcaciega

class Universo(
    var nombre:String?,
    var planetas: ArrayList<String> =arrayListOf<String>()
) {
    override fun toString(): String {
        return "${nombre}"
    }
    fun toString1():String{
        return "${planetas}"
    }
}