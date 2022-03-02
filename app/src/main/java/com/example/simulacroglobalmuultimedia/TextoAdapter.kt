package com.example.simulacroglobalmuultimedia

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simulacroglobalmuultimedia.databinding.ActivityMainBinding
import com.example.simulacroglobalmuultimedia.databinding.ItemUsuarioBinding




class TextoAdapter(var listaUsuarios: List<Result>):RecyclerView.Adapter <TextoAdapter.TextoViewHolder>()  {
    class TextoViewHolder(var itemUsuarioBinding : ItemUsuarioBinding) : RecyclerView.ViewHolder(itemUsuarioBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {
        holder.itemUsuarioBinding.textView1.text = listaUsuarios[position].name.first+" "+listaUsuarios[position].name.last


        holder.itemUsuarioBinding.textView1.setOnClickListener {
            MainActivity.mostrarciudad(listaUsuarios[position].location.city)
        }
        //holder.itemUsuarioBinding.textView1.text=listaUsuarios[position].gender
    }
    override fun getItemCount(): Int {

        return listaUsuarios.size
    }

}