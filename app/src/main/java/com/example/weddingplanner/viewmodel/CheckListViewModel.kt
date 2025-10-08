package com.example.weddingplanner.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weddingplanner.model.ChecklistItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckListViewModel: ViewModel(){

    //backing property

    private val _uiState = MutableStateFlow(CheckListUiState())
    //mutableStateFlow take object not class

    //stateflow is immutable->this is shown to users only thorough ui
    val uiState: StateFlow<CheckListUiState> = _uiState.asStateFlow()

    //add or update task
    fun addOrUpdate(){
        val state = _uiState.value
        //_uiState.value means CheckListUiState
        val text = _uiState.value.currentText.trim() //removing spaces

        if (text.isNotBlank()) {
            val updatedList = if (state.editId != null) {
                state.checklist.map {
                    if (it.id == state.editId) it.copy(text = text) else it
                }
            } else {
                val newItem = ChecklistItem(
                    id = (state.checklist.maxOfOrNull { it.id } ?: 0) + 1,
                    text = text
                )
                state.checklist + newItem
            }

            _uiState.value = state.copy(
                checklist = updatedList,
                currentText = "",
                editId = null
            )
        }

    }
    //edit task
    fun update(id: Int, newText: String) {
        val state = _uiState.value
        _uiState.value = state.copy(
            checklist = state.checklist.map {
                if (it.id == id) it.copy(text = newText) else it
            }
        )
    }
    //marking them when completed
    fun markedDone(id:Int){
        val state = _uiState.value
        _uiState.value = state.copy(
            checklist = state.checklist.map {
                if (it.id == id) it.copy(isDone = !it.isDone) else it
            }
        )

    }

    fun delete(id: Int){
        val state = _uiState.value
        _uiState.value = state.copy(
            checklist = state.checklist.filterNot { it.id == id }
        )
    }

    fun onTextChange(newText: String) {
        _uiState.value = _uiState.value.copy(currentText = newText)
    }

    init {
        _uiState.value = _uiState.value.copy(
            checklist = listOf(
                ChecklistItem(1, "Book Wedding Venue"),
                ChecklistItem(2, "Hire Photographer"),
                ChecklistItem(3, "Book Caterer"),
                ChecklistItem(4, "Arrange Mehendi Artist"),
                ChecklistItem(5, "Plan Sangeet Ceremony"),
                ChecklistItem(6, "Book Honeymoon Package")
            )
        )
    }


}

data class CheckListUiState(
    val checklist: List<ChecklistItem> = emptyList(),
    val currentText: String = "",
    val editId: Int? = null
)