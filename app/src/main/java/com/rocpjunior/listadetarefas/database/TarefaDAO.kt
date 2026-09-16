package com.rocpjunior.listadetarefas.database

import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract.Data
import android.util.Log
import com.rocpjunior.listadetarefas.model.Tarefa

class TarefaDAO(context: Context): ITarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {

        val conteudos = ContentValues()
        conteudos.put(DatabaseHelper.COLUNA_DESCRICAO, tarefa.descricao)

        try {
            escrita.insert(
                DatabaseHelper.NOME_TABELA,
                null,
                conteudos
            )
            Log.i("info_db", "Sucesso ao salvar tarefa =D")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("", "Aconteceu alguma coisa de errado ao salvar tarefa D=")
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        val args = arrayOf(tarefa.idTarefa.toString())
        val conteudo = ContentValues()
        conteudo.put(DatabaseHelper.COLUNA_DESCRICAO, tarefa.descricao)

        try {
            escrita.update(DatabaseHelper.NOME_TABELA, conteudo,"${DatabaseHelper.COLUNA_ID} = ?", args)
            Log.i("info_db", "Sucesso ao atualizar tarefa =D")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Aconteceu alguma coisa de errado ao atualizar tarefa D=")
            return false
        }
        return true
    }


    override fun remover(idTarefa: Int): Boolean {
        val args = arrayOf(idTarefa.toString())

        try {
            escrita.delete(DatabaseHelper.NOME_TABELA,"${DatabaseHelper.COLUNA_ID} = ?", args)
            Log.i("info_db", "Sucesso ao remover tarefa =D")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Aconteceu alguma coisa de errado ao remover tarefa D=")
            return false
        }
        return true
    }


    override fun listar(): List<Tarefa> {
        val listaTarefas = mutableListOf<Tarefa>()

        val sql = "SELECT ${DatabaseHelper.COLUNA_ID}, " +
                "${DatabaseHelper.COLUNA_DESCRICAO}, " +
                " strftime('%d/%m/%Y %H:%M', ${DatabaseHelper.COLUNA_DATA_CADASTRO}) ${DatabaseHelper.COLUNA_DATA_CADASTRO} " +
                "FROM ${DatabaseHelper.NOME_TABELA}"

        val cursor = leitura.rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex(DatabaseHelper.COLUNA_ID)
        val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.COLUNA_DESCRICAO)
        val indiceData = cursor.getColumnIndex(DatabaseHelper.COLUNA_DATA_CADASTRO)

        while (cursor.moveToNext()){
            val idTarefa = cursor.getInt(indiceId)
            val descricao = cursor.getString(indiceDescricao)
            val data = cursor.getString(indiceData)

            listaTarefas.add(Tarefa(idTarefa, descricao, data))
        }
        return listaTarefas
    }
}