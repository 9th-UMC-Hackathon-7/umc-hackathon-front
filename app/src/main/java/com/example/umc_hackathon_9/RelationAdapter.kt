package com.example.umc_hackathon_9

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RelationAdapter(
    private val items: MutableList<RelationMessage>,
    private val onChipClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RelationAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_relation, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.tvPrefix.text = "${item.targetName}와 관계는 "

        val rel = item.relation
        if (rel.isNullOrBlank()) {
            holder.tvChip.text = "" // ✅ 기본 흰 동그라미
            holder.tvChip.setBackgroundResource(R.drawable.bg_relation_chip_unselected)
            holder.tvChip.minWidth = holder.dp(40)
        } else {
            holder.tvChip.text = rel
            holder.tvChip.setBackgroundResource(R.drawable.bg_relation_chip_selected)
        }

        holder.tvChip.setOnClickListener { onChipClick(position) }
        // 문장 전체 눌러도 팝업 뜨게 하고 싶으면:
        holder.itemView.setOnClickListener { onChipClick(position) }
    }

    override fun getItemCount(): Int = items.size

    fun setRelation(position: Int, relation: String) {
        if (position !in items.indices) return
        items[position].relation = relation
        notifyItemChanged(position)
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvPrefix: TextView = v.findViewById(R.id.tvPrefix)
        val tvChip: TextView = v.findViewById(R.id.tvRelationChip)

        fun dp(value: Int): Int =
            (value * itemView.resources.displayMetrics.density).toInt()
    }
}
