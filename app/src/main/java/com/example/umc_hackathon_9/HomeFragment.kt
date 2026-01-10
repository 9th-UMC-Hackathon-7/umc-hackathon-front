package com.example.umc_hackathon_9

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: UmcAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvUmc)



        val mock = mutableListOf(
            UmcItem(1, "침묵이 만든 불안과 압박", "업무에 치여 답장이 늦어진 '나'와...", "2026.01.10", 25),
            UmcItem(2, "본심을 가린 가시 돋친 대화법", "피로함에 무심코 던진 날카로운 말투가...", "2026.01.09", 0),
            UmcItem(3, "시간 개념의 차이가 부른 신뢰의 위기", "피치 못할 사정으로 약속에 늦은 '나'...", "2026.12.30", 0)
        )

        updateEmptyState(mock)




        adapter = UmcAdapter(mock) { item ->
            // TODO: 상세 화면 이동
        }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        val swipe = SwipeController(
            context = requireContext(),
            onArchive = { pos ->
                adapter.archive(pos)
                // 원하면 토스트/스낵바
            },
            onDone = { pos ->
                adapter.markDone(pos)
                ResolveBottomSheetFragment().show(parentFragmentManager, "resolve_sheet")
            },

            onDelete = { pos ->
                adapter.removeAt(pos)
                updateEmptyState(adapter.getItems())
            }

        )
        swipe.attachToRecyclerView(rv)





    }

    private fun showResolveBottomSheet() {
        val sheet = ResolveBottomSheetFragment()
        sheet.show(parentFragmentManager, sheet.tag)
    }

    private fun updateEmptyState(items: List<UmcItem>) {
        val rv = requireView().findViewById<RecyclerView>(R.id.rvUmc)
        val empty = requireView().findViewById<View>(R.id.emptyView)

        val isEmpty = items.isEmpty()
        rv.visibility = if (isEmpty) View.GONE else View.VISIBLE
        empty.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }


}



