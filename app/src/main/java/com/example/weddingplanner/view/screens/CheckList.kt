package com.example.weddingplanner.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weddingplanner.R
import com.example.weddingplanner.model.ChecklistItem
import com.example.weddingplanner.ui.theme.Apricot
import com.example.weddingplanner.ui.theme.Ivor
import com.example.weddingplanner.ui.theme.Peach
import com.example.weddingplanner.viewmodel.CheckListViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ChecklistItemRow(
    item: ChecklistItem,
    onToggleDone: () -> Unit,
    onUpdate: (String) -> Unit,
    onDelete: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var updatedText by remember { mutableStateOf(item.text) }

    // 🪄 Alert Dialog when item clicked
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Edit Item") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(
                        value = updatedText,
                        onValueChange = { updatedText = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (updatedText.isNotBlank()) {
                        onUpdate(updatedText)
                        showDialog = false
                    }
                }) {
                    Text("Update")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            },
            containerColor = Ivor,
            shape = RoundedCornerShape(16.dp)
        )
    }

    // 🪄 Card Layout for Item
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Ivor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { showDialog = true }, // open dialog on click
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // ✅ Small circle checkbox
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(if (item.isDone) Color.Gray else Color.Transparent)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable { onToggleDone() }
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 📝 Text with line-through when done
            Text(
                text = item.text,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if (item.isDone) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (item.isDone) Color.Gray else Color.Black
                ),
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { onDelete() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    //textAlign = Alignment.End
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListScreen(
    viewModel: CheckListViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent)
        systemUiController.isStatusBarVisible = true
    }


    var showDialog by remember { mutableStateOf(false) }
    var newText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Peach)
    ) {
        // 🩷 Custom Top Bar
        CustomTopBar(
            title = "Time Table",
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.pushpin),
                    contentDescription = "Pin",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(36.dp)
                )
            },
            onBackClick = onBackClick
        )


        // 📋 Checklist List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
            .padding(horizontal = 8.dp) // side padding
                .padding(top = 110.dp),
            contentPadding = PaddingValues(
                top = 12.dp,    // space above first item
                bottom = 80.dp  // space below last item (for FAB)
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.checklist, key = { it.id }) { item ->
                ChecklistItemRow(
                    item = item,
                    onToggleDone = { viewModel.markedDone(item.id) },
                    onUpdate = { newText -> viewModel.update(item.id, newText) },
                    onDelete = { viewModel.delete(item.id) }
                )
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            shape = CircleShape,
            containerColor = Ivor,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.Black)
        }

        // 🪄 Dialog for Adding Item
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newText.isNotBlank()) {
                                viewModel.onTextChange(newText)
                                viewModel.addOrUpdate()
                                newText = ""
                                showDialog = false
                            }
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) { Text("Cancel") }
                },
                title = { Text("Add Checklist Item") },
                text = {
                    TextField(
                        value = newText,
                        onValueChange = { newText = it },
                        placeholder = { Text("Enter item...") },
                        singleLine = true
                    )
                },
                containerColor = Ivor,
                shape = RoundedCornerShape(20.dp)
            )
        }

    }
}



@Composable
fun CustomTopBar(
    title: String,
    trailingIcon: (@Composable (() -> Unit))? = null, // optional custom icon
    onBackClick: () -> Unit = {} // back button action
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .background(Ivor)
    ) {
        // Title at center
        Text(
            title,
            fontSize = 38.sp,
//            fontFamily = PoppinsMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )

        // Back icon at start, vertically centered
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(36.dp)
            )
        }

        // Optional trailing icon (not a button)
        trailingIcon?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            ) {
                it()
            }
        }
    }
}






