package com.larissa.ecoenergy.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.larissa.ecoenergy.R

class PensamentoAdapter(private val pensamentos: List<Pensamento>) : RecyclerView.Adapter<PensamentoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewPensamento: TextView = view.findViewById(R.id.textViewPensamento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pensamento, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewPensamento.text = pensamentos[position].opiniao
    }

    override fun getItemCount() = pensamentos.size




}