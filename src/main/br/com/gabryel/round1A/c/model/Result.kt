package br.com.gabryel.round1A.c.model

sealed class Result {
    data class Sucess(val turns: Int): Result() {
        override fun toString() = turns.toString()
    }

    object Impossible: Result() {
        override fun toString() = "IMPOSSIBLE"
    }
}