package com.example.simulacroglobalmuultimedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simulacroglobalmuultimedia.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var adapter : TextoAdapter
    private val viewModel:MainActivityViewModel by viewModels()

    companion object{
        private lateinit var binding: ActivityMainBinding

        fun mostrarciudad(texto:String){
            Snackbar.make(binding.root,texto,Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycledview.layoutManager = LinearLayoutManager(this)
        viewModel.getUsuarios()

        binding.botonTodos.setOnClickListener {
           viewModel.obtenerUsuarios()
        }
        binding.botonChicas.setOnClickListener {
            viewModel.obtenerFemenino()
        }
        binding.botonChicos.setOnClickListener {
            viewModel.obtenerMasculino()
        }
        initObsever()
    }

     fun initObsever() {
         viewModel.responseUsuarios.observe(this){usuariosResponse ->

             adapter= TextoAdapter(usuariosResponse.results)
             binding.recycledview.adapter=adapter

         }


    }


}