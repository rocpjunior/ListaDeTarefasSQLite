package com.rocpjunior.listadetarefas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rocpjunior.listadetarefas.adapter.TarefaAdapter
import com.rocpjunior.listadetarefas.database.TarefaDAO
import com.rocpjunior.listadetarefas.databinding.ActivityMainBinding
import com.rocpjunior.listadetarefas.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarTarefaActivity::class.java)
            startActivity(intent)
        }

        tarefaAdapter = TarefaAdapter(
            {id -> excluirTarefa(id)},
            {tarefa -> editarTarefa(tarefa)}
        )
        binding.rvTarefas.adapter =  tarefaAdapter
        binding.rvTarefas.layoutManager = LinearLayoutManager(this)
    }

    private fun editarTarefa(tarefa: Tarefa) {
        val intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)
    }

    private fun excluirTarefa(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Confirmar Exclusão")
        alertBuilder.setMessage("Deseja excluir a tarefa?")
        alertBuilder.setPositiveButton("Sim"){_, _ ->
            val tarefaDAO = TarefaDAO(this)
            if (tarefaDAO.remover(id)) {
                atualizarListaTarefa()
                Toast.makeText(this, "Tarefa removida com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"A tarefa não foi removida D=", Toast.LENGTH_SHORT).show()
            }
        }
        alertBuilder.setNegativeButton("Não"){_, _ ->}
        alertBuilder.create().show()
    }

    private fun atualizarListaTarefa(){
        val tarefaDAO = TarefaDAO(this)
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)
    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefa()
    }
}