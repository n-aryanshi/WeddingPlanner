package com.example.weddingplanner.model

data class ChecklistItem(
    val id: Int,
    var text: String,
    var isDone: Boolean = false
)