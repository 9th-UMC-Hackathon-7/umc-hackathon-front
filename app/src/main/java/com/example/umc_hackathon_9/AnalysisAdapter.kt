package com.example.umc_hackathon_9

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_hackathon_9.data.entities.AnalysisData
import com.example.umc_hackathon_9.databinding.AnalysisItemBinding

class AnalysisAdapter(private val items: List<AnalysisData>) :
    RecyclerView.Adapter<AnalysisAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AnalysisItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AnalysisItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvAnalysisTitle.text = items[position].title
        holder.binding.tvAnalysisContent.text = items[position].content
    }

    override fun getItemCount() = items.size
}