package com.example.weddingplanner.view.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weddingplanner.R
import com.example.weddingplanner.ui.theme.Ivor
import com.example.weddingplanner.ui.theme.Peach
import com.example.weddingplanner.ui.theme.RosyPink

data class Venue(
    val name: String,
    val pricePerPlate: Int,
    val totalPrice: Int,
    val guests: String,
    val rooms: Int,
    val imageRes: Int, // drawable resource
    val rating: Double
)


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun VenueListScreen(onBackClick: () -> Unit = {}) {
    val sampleVenues = listOf(
        Venue("Park Boulevard Hotel", 4500, 450000, "250–1000 pax", 45, R.drawable.hotel1, 5.0),
        Venue("The Leela Palace", 6000, 600000, "25–300 pax", 254, R.drawable.hotel2, 4.8),
        Venue("The Leela Ambience", 3299, 330000, "70–1800 pax", 480, R.drawable.hotel3, 4.1),
        Venue("Royal Palace", 400, 120000, "Up to 300 pax", 40, R.drawable.hotel4, 4.9),
        Venue("Sunset Lawn, Jaipur", 400, 80000, "Up to 200 pax", 25, R.drawable.hotel5, 4.7),
        Venue("Emerald Banquet, Mumbai", 375, 150000, "Up to 400 pax", 60, R.drawable.hotel6, 4.8),
        Venue("Lotus Garden, Pune", 400, 60000, "Up to 150 pax", 30, R.drawable.hotel7, 4.6),
        Venue("Grand Orchid, Goa", 400, 200000, "Up to 500 pax", 100, R.drawable.hotel8, 4.9),
        Venue("Rosewood Hall, Lucknow", 500, 90000, "Up to 180 pax", 35, R.drawable.hotel9, 4.5),
        Venue("Blue Diamond, Chandigarh", 440, 110000, "Up to 250 pax", 50, R.drawable.hotel10, 4.8)
    )

    // User input for filters
    var budgetInput by remember { mutableStateOf("") }
    var guestsInput by remember { mutableStateOf("") }

    // State to hold filtered list
    var filteredVenues by remember { mutableStateOf(sampleVenues) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wedding Venues", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = onBackClick) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = Ivor
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // ---------------- Filter Inputs ----------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = budgetInput,
                    onValueChange = { budgetInput = it },
                    label = { Text("Maxn Budget / plate", color = Color.Gray) },
                    modifier = Modifier.weight(1f),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = guestsInput,
                    onValueChange = { guestsInput = it },
                    label = { Text("Max Guests", color = Color.Gray) },
                    modifier = Modifier.weight(1f),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Black,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        // parse inputs once
                        val maxBudget = budgetInput.toIntOrNull()        // null means "no filter"
                        val requiredGuests = guestsInput.toIntOrNull()  // null means "no filter"

                        filteredVenues = sampleVenues.filter { venue ->
                            // robustly extract the last number from venue.guests (works for "250–1000 pax" and "Up to 300 pax")
                            val maxGuests = Regex("""(\d+)""")
                                .findAll(venue.guests)
                                .map { it.value }
                                .lastOrNull()
                                ?.toIntOrNull() ?: 0


                            // If you want "Max Budget", change to: val budgetCheck = minBudget == null || venue.pricePerPlate <= minBudget
                            val budgetCheck = maxBudget == null || venue.pricePerPlate <= maxBudget

                            // Guests logic: venue must accommodate at least requiredGuests
                            val guestsCheck = requiredGuests == null || maxGuests >= requiredGuests

                            budgetCheck && guestsCheck
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RosyPink),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Apply", color = Ivor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = {
                        budgetInput = ""
                        guestsInput = ""
                        filteredVenues = sampleVenues
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Reset", color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(4.dp))


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 2.dp) // small horizontal padding only
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredVenues) { venue ->
                    VenueCard(venue)
                }
            }
        }
    }
}



@Composable
fun VenueCard(venue: Venue) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = RosyPink),
    ) {
        Column {
            Image(
                painter = painterResource(id = venue.imageRes),
                contentDescription = venue.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = venue.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )

                Row(){
                    Text("Total: ₹${venue.totalPrice},", fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.width(6.dp))
                    Text("⭐ ${venue.rating}", fontSize = 13.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                }

                Text("₹${venue.pricePerPlate} per plate", fontWeight = FontWeight.Medium, color = Color.Black,)
                Text("Guests: ${venue.guests}", fontSize = 13.sp, color = Color.Black,)
                Text("Rooms: ${venue.rooms}", fontSize = 13.sp, color = Color.Black,)
            }
        }
    }
}






