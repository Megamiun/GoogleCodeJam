package br.com.gabryel.util.double

import java.text.DecimalFormat

fun Double.toGoogleFormat(): String = DecimalFormat("0.#######").format(this)