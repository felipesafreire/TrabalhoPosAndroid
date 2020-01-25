package br.aula.agenda.db

import android.content.Context
import br.fib.bar.db.Bar
import br.fib.bar.db.ConstantDB
import database
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.*
import timber.log.Timber

class BarRepository(val context: Context) {


    fun findAll(): ArrayList<Bar> = context.database.use {

        val bares = ArrayList<Bar>()

        select(
            ConstantDB.DB_TABLE_BARES,
            "id",
            "nome",
            "horarios",
            "imagem"
        )
            .parseList(object : MapRowParser<List<Bar>> {
                override fun parseRow(columns: Map<String, Any?>): List<Bar> {
                    val bar = Bar(
                        id = columns.getValue("id").toString()?.toLong(),
                        nome = columns.getValue("nome") as String,
                        horarios = columns.getValue("horarios") as String,
                        imagem = columns.getValue("imagem").toString()
                    )
                    bares.add(bar)
                    return bares
                }
            })

        bares
    }

    fun create(bar: Bar) = context.database.use {
        insert(
            ConstantDB.DB_TABLE_BARES,
            "nome" to bar.nome,
            "horarios" to bar.horarios,
            "imagem" to bar.imagem
        )
    }

    fun update(bar: Bar) = context.database.use {
        val updateResult = update(
            ConstantDB.DB_TABLE_BARES,
            "nome" to bar.nome,
            "horarios" to bar.horarios,
            "imagem" to bar.imagem
        )
            .whereArgs("id = {id}", "id" to bar.id).exec()

        Timber.d("Update result code is $updateResult")
    }

    fun delete(id: Int) = context.database.use {
        delete(
            ConstantDB.DB_TABLE_BARES,
            whereClause = "id = {barId}",
            args = *arrayOf("barId" to id)
        )
    }
}