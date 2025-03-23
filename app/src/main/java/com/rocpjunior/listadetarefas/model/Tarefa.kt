package com.rocpjunior.listadetarefas.model

import java.io.Serializable

data class Tarefa(
    val idTarefa: Int,
    val descricao: String,
    val dataCadastroTarefa: String
): Serializable