package br.com.gabryel.round1C.a

data class Pancake(val radius: Int, val height: Int) {

    val upperArea = Math.PI * Math.pow(radius.toDouble(), 2.0)

    val sideArea = 2 * Math.PI * radius * height

    val totalArea = upperArea + sideArea
}