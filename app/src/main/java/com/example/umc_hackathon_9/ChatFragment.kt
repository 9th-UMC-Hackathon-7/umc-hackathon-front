package com.example.umc_hackathon_9

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var rvChat: RecyclerView
    private lateinit var adapter: ChatAdapter

    private lateinit var btnAnalyze: MaterialButton
    private lateinit var etMessage: EditText

    private val selectedUris = mutableListOf<Uri>() // 5~10장 저장

    private var sheetDialog: BottomSheetDialog? = null

    private val pickPhotosLauncher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris ->
            if (uris.isNullOrEmpty()) return@registerForActivityResult

            if (uris.size < 5) {
                Toast.makeText(requireContext(), "사진은 최소 5장 선택해야 해요!", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }

            selectedUris.clear()
            selectedUris.addAll(uris)

            // ✅ 선택 끝나자마자: 첫 장을 채팅에 "보낸 것처럼" 띄우기
            adapter.add(ChatAdapter.ChatItem.ImagePreview(selectedUris.first()))
            scrollToBottom()

            // 바텀시트 닫기
            sheetDialog?.dismiss()
            sheetDialog = null

            // 버튼 상태 갱신(민트 꽉 찬 버튼)
            updateAnalyzeButtonState()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.showBottomNav(false)

        rvChat = view.findViewById(R.id.rvChat)
        btnAnalyze = view.findViewById(R.id.btnAnalyze)
        etMessage = view.findViewById(R.id.etMessage)

        adapter = ChatAdapter(mutableListOf())
        rvChat.layoutManager = LinearLayoutManager(requireContext())
        rvChat.adapter = adapter

        view.findViewById<View>(R.id.btnPlus).setOnClickListener {
            showUploadBottomSheet()
        }


        // ✅ 텍스트 입력하면 버튼 상태도 바뀌게
        etMessage.addTextChangedListenerSimple {
            updateAnalyzeButtonState()
        }

        // ✅ "내 상황 분석하기" 버튼 = 전송 트리거로 사용 (사진/텍스트 같이 보내기)
        btnAnalyze.setOnClickListener {
            sendCurrentMessage()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, OpponentMbtiFragment())
            transaction.commit()
        }

        updateAnalyzeButtonState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.showBottomNav(true)
    }

    private fun showUploadBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_upload, null)
        dialog.setContentView(sheetView)

        // bottom_sheet_upload.xml에 있어야 함
        sheetView.findViewById<ImageButton>(R.id.btnPickPhotos).setOnClickListener {
            pickPhotosLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        sheetDialog = dialog
        dialog.show()
    }

    private fun sendCurrentMessage() {
        val text = etMessage.text?.toString()?.trim().orEmpty()

        // 사진도 텍스트도 없으면 아무것도 안 함
        if (selectedUris.isEmpty() && text.isEmpty()) return

        // ✅ 사진은 이미 "첫 장" 미리보기로 보냈으니
        // 여기서는 텍스트가 있으면 텍스트만 추가로 보냄 (같이 보내는 느낌)
        if (text.isNotEmpty()) {
            adapter.add(ChatAdapter.ChatItem.TextMsg(text))
        }

        // 입력 초기화 (사진 선택 상태도 초기화)
        etMessage.setText("")
        selectedUris.clear()

        updateAnalyzeButtonState()
        scrollToBottom()
    }

    private fun updateAnalyzeButtonState() {
        val hasText = etMessage.text?.toString()?.trim()?.isNotEmpty() == true
        val hasPhotos = selectedUris.isNotEmpty()

        val enabled = hasText || hasPhotos
        btnAnalyze.isEnabled = enabled

        if (enabled) {
            // ✅ 피그마처럼 꽉 찬 민트
            btnAnalyze.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.main))
            btnAnalyze.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            btnAnalyze.strokeWidth = 0
        } else {
            // ✅ 기본(연민트 + 민트 테두리)
            btnAnalyze.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent))
            btnAnalyze.setTextColor(ContextCompat.getColor(requireContext(), R.color.main))
            btnAnalyze.strokeWidth = dp(1)
            btnAnalyze.strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.main)
        }
    }

    private fun scrollToBottom() {
        rvChat.post {
            if (adapter.itemCount > 0) rvChat.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    // 간단 텍스트워처
    private fun EditText.addTextChangedListenerSimple(onChanged: () -> Unit) {
        this.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { onChanged() }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }
}
