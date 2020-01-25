package br.fib.bar.db

import java.io.Serializable

data class Bar(
    var id: Long = 0,
    var nome: String? = null,
    var horarios: String? = null,
    var imagem: String? = null
) : Serializable {

    override fun toString(): String {
        return this.nome.toString()
    }

}