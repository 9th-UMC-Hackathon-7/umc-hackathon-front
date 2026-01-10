package com.example.umc_hackathon_9

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val items: MutableList<ChatItem> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    sealed class ChatItem {
        data class ImagePreview(val uri: Uri) : ChatItem()
        data class TextMsg(val text: String) : ChatItem()

        // ✅ 관계 선택 프롬프트 (기본은 흰 동그라미)
        data class RelationPrompt(
            val targetName: String,
            var relation: String? = null
        ) : ChatItem()
    }

    private companion object {
        const val VT_IMAGE = 1
        const val VT_TEXT = 2
        const val VT_RELATION = 3
    }

    // ✅ 관계칩 클릭 리스너
    private var onRelationChipClick: ((Int) -> Unit)? = null
    fun setOnRelationChipClickListener(listener: (pos: Int) -> Unit) {
        onRelationChipClick = listener
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ChatItem.ImagePreview -> VT_IMAGE
            is ChatItem.TextMsg -> VT_TEXT
            is ChatItem.RelationPrompt -> VT_RELATION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inf = LayoutInflater.from(parent.context)
        return when (viewType) {
            VT_IMAGE -> ImageVH(inf.inflate(R.layout.item_chat_image, parent, false))
            VT_TEXT -> TextVH(inf.inflate(R.layout.item_chat_text, parent, false))
            else -> RelationVH(inf.inflate(R.layout.item_chat_relation, parent, false), onRelationChipClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ChatItem.ImagePreview -> (holder as ImageVH).bind(item)
            is ChatItem.TextMsg -> (holder as TextVH).bind(item)
            is ChatItem.RelationPrompt -> (holder as RelationVH).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun add(item: ChatItem) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    // ✅ 추가 + 위치 리턴 (ChatFragment에서 relationPos 받으려고)
    fun addAndReturnPosition(item: ChatItem): Int {
        items.add(item)
        val pos = items.lastIndex
        notifyItemInserted(pos)
        return pos
    }

    // ✅ 관계 업데이트
    fun updateRelationAt(position: Int, relation: String) {
        val item = items.getOrNull(position) as? ChatItem.RelationPrompt ?: return
        item.relation = relation
        notifyItemChanged(position)
    }

    private class ImageVH(v: View) : RecyclerView.ViewHolder(v) {
        private val iv = v.findViewById<ImageView>(R.id.ivPreview)
        fun bind(item: ChatItem.ImagePreview) {
            iv.setImageURI(item.uri)
            iv.alpha = 1f
        }
    }

    private class TextVH(v: View) : RecyclerView.ViewHolder(v) {
        private val tv = v.findViewById<TextView>(R.id.tvMsg)
        fun bind(item: ChatItem.TextMsg) {
            tv.text = item.text
            tv.alpha = 1f
        }
    }

    private class RelationVH(
        v: View,
        private val onChipClick: ((Int) -> Unit)?
    ) : RecyclerView.ViewHolder(v) {

        private val tvPrefix = v.findViewById<TextView>(R.id.tvPrefix)
        private val tvChip = v.findViewById<TextView>(R.id.tvRelationChip)
        private val tvSuffix = v.findViewById<TextView>(R.id.tvSuffix)

        init {
            tvChip.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) onChipClick?.invoke(pos)
            }
            // 문장 전체 눌러도 뜨게(원하면)
            v.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) onChipClick?.invoke(pos)
            }
        }

        fun bind(item: ChatItem.RelationPrompt) {
            tvPrefix.text = "${item.targetName}와 관계는 "
            tvSuffix.text = " 이에요."

            val rel = item.relation
            if (rel.isNullOrBlank()) {
                // ✅ 기본: 흰 동그라미
                tvChip.text = ""
                tvChip.setBackgroundResource(R.drawable.bg_relation_chip_unselected)
            } else {
                tvChip.text = rel
                tvChip.setBackgroundResource(R.drawable.bg_relation_chip_selected)
            }
        }
    }
}
