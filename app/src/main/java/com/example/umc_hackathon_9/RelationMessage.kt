package com.example.umc_hackathon_9

data class RelationMessage(
    val id: Long,
    val targetName: String,   // 예: 예림이
    var relation: String? = null  // null이면 미선택(흰 동그라미)
)

