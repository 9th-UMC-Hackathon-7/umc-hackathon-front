package com.example.umc_hackathon_9

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_hackathon_9.network.ApiClient
import com.example.umc_hackathon_9.network.ProjectModels
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var rv: RecyclerView
    private lateinit var emptyView: View

    private val items = mutableListOf<UmcItem>()
    private lateinit var adapter: UmcAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.rvUmc)
        emptyView = view.findViewById(R.id.emptyView)

        adapter = UmcAdapter(items) { item ->
            // TODO: 채팅방 상세 화면 이동 등
        }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        val swipe = SwipeController(
            context = requireContext(),
            onArchive = { pos ->
                adapter.archive(pos)
                updateEmptyState()
            },
            onDone = { pos ->
                adapter.markDone(pos)
                ResolveBottomSheetFragment().show(parentFragmentManager, "resolve_sheet")
            },
            onDelete = { pos ->
                adapter.removeAt(pos)
                updateEmptyState()
            }
        )
        swipe.attachToRecyclerView(rv)

        // 🔥 로그인/레포지토리 다 빼고, 직접 서버 호출
        loadRoomsRaw()
    }

    private fun loadRoomsRaw() {
        val accessToken = "토큰자리->env참고"

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = ApiClient.roomApi.getMainPage("Bearer $accessToken")

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.resultType == "SUCCESS" && body.success != null) {
                        bindMainPage(body.success)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            body?.error?.reason ?: "메인 페이지 조회 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateEmptyState()
                    }
                } else {
                    val errorStr = response.errorBody()?.string()
                    Toast.makeText(
                        requireContext(),
                        "서버 에러: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    // 디버깅용
                    android.util.Log.e("HomeFragment", "room error: $errorStr")
                    updateEmptyState()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "네트워크 오류가 발생했습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                updateEmptyState()
            }
        }
    }

    private fun bindMainPage(success: ProjectModels.MainPageSuccessResponse) {
        items.clear()

        success.rooms.forEach { room ->
            val item = UmcItem(
                id = room.id,
                title = room.title,
                desc = room.body,
                dateText = "",        // 서버에서 날짜 안 주면 일단 빈 문자열
                percent = room.gauge  // 필요하면 reliefGauge로 바꿔도 됨
            )
            items.add(item)
        }

        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    private fun updateEmptyState() {
        val isEmpty = items.isEmpty()
        rv.visibility = if (isEmpty) View.GONE else View.VISIBLE
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}
