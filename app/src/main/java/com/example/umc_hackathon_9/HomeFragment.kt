package com.example.umc_hackathon_9

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvUmc)

        // ✅ 테스트: 비었을 때 empty state 보려면 이걸로 바꿔
        // val items = mutableListOf<UmcItem>()
        val items = mutableListOf(
            UmcItem(1, "침묵이 만든 불안과 압박", "업무에 치여 답장이 늦어진 '나'와...", "2026.01.10", 25),
            UmcItem(2, "본심을 가린 가시 돋친 대화법", "피로함에 무심코 던진 날카로운 말투가...", "2026.01.09", 0),
            UmcItem(3, "시간 개념의 차이가 부른 신뢰의 위기", "피치 못할 사정으로 약속에 늦은 '나'...", "2026.12.30", 0),
        )

        // ✅ lateinit 제거: 여기서 바로 생성
        val adapter = UmcAdapter(items) { item ->
            // TODO: 상세 화면 이동
        }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        // ✅ 처음에도 empty state 반영
        updateEmptyState(view, items)

        val swipe = SwipeController(
            context = requireContext(),
            onArchive = { pos ->
                adapter.archive(pos)
                updateEmptyState(view, items)
            },
            onDone = { pos ->
                adapter.markDone(pos)
                ResolveBottomSheetFragment().show(parentFragmentManager, "resolve_sheet")
            },
            onDelete = { pos ->
                adapter.removeAt(pos)
                updateEmptyState(view, items)
            }
        )
        swipe.attachToRecyclerView(rv)
    }

    private fun updateEmptyState(root: View, items: List<UmcItem>) {
        val rv = root.findViewById<RecyclerView>(R.id.rvUmc)
        val empty = root.findViewById<View>(R.id.emptyView)

        val isEmpty = items.isEmpty()
        rv.visibility = if (isEmpty) View.GONE else View.VISIBLE
        empty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}
