package com.example.datomate.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.datomate.R


@Composable
fun AppLogoBar(size: Int, alignment: Alignment) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo3),
            contentDescription = "Logo DATOMATE",
            modifier = Modifier.size(size.dp)
        )
    }
}

@Composable
fun Title(text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(bottom = 32.dp)
    )
}

// Definición de ítems de la barra (ejemplo)
data class BottomNavItem(
    val iconId: Int,
    val route: String
)

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    currentRoute: String,
    onItemSelected: (String) -> Unit
) {
    val containerColor = MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(containerColor, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = item.route == currentRoute

            // -- CAMBIAR A MATHERIAL.SCHEME!!!!! --
            // Determina el color y el fondo basados en si está seleccionado
            val itemColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f)
            val itemBackground = if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(itemBackground)
                    .clickable { onItemSelected(item.route) }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = item.iconId),
                    contentDescription = item.route,
                    tint = itemColor,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}