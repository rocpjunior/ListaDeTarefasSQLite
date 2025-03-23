package com.rocpjunior.listadetarefas.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context, NOME_BANCO_DE_DADOS, null, VERSAO_BANCO_DE_DADOS
) {
    companion object {
        const val NOME_BANCO_DE_DADOS = "ListaDeTarefas.db"
        const val VERSAO_BANCO_DE_DADOS = 1
        const val NOME_TABELA = "tarefas"
        const val COLUNA_ID = "id_tarefa"
        const val COLUNA_DESCRICAO = "descricao"
        const val COLUNA_DATA_CADASTRO = "data_cadastro"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE $NOME_TABELA (" +
                "$COLUNA_ID INTEGER not NULL PRIMARY KEY AUTOINCREMENT," +
                "$COLUNA_DESCRICAO VARCHAR (70)," +
                "$COLUNA_DATA_CADASTRO DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"

        try {
            db?.execSQL(sql)
            Log.i("info_db", "Sucesso ao criar tabela =D")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Aconteceu alguma coisa de errado na criacao da tabela D=")
        }
    }


    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}