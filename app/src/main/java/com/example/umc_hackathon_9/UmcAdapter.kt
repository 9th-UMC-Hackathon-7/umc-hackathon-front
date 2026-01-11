package com.example.umc_hackathon_9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UmcAdapter(
    private val items: MutableList<UmcItem>,
    private val onItemClick: (UmcItem) -> Unit
) : RecyclerView.Adapter<UmcAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvItemTitle)
        val tvDesc: TextView = view.findViewById(R.id.tvItemDesc)
        val tvDate: TextView = view.findViewById(R.id.tvItemDate)
        val tvPercent: TextView = view.findViewById(R.id.tvPercent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_umc_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvDesc.text = item.desc
        holder.tvDate.text = item.dateText
        holder.tvPercent.text = "${item.percent}%"

        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    fun removeAt(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun markDone(pos: Int) {
        // 필요하면 done 상태 필드 추가해서 업데이트 처리
        notifyItemChanged(pos)
    }

    fun archive(pos: Int) {
        notifyItemChanged(pos)
    }

    fun getItems(): List<UmcItem> = items

}
