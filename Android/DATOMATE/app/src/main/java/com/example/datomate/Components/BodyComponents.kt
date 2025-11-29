package com.example.datomate.Components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datomate.R
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale


@Composable
fun PrimaryTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Usará color_primary_green
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SecondaryOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary)
        )
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun ForgotPasswordLink(onClick: () -> Unit) {
    TextButton(onClick = onClick, modifier = Modifier.padding(top = 8.dp).wrapContentSize(Alignment.CenterEnd)) {
        Text(
            "¿Olvidaste tu contraseña?",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun Space(space: Int){
    Spacer(modifier = Modifier.height(space.dp))
}

@Composable
fun SpaceW(space: Int){
    Spacer(modifier = Modifier.width(space.dp))
}

@Composable
fun AccountNavigationLink(
    prefixText: String,
    linkText: String,
    onLinkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        // Texto normal
        Text(
            text = prefixText,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
            style = MaterialTheme.typography.bodySmall
        )

        // Texto del enlace
        Text(
            text = linkText,
            color = MaterialTheme.colorScheme.primary, // Color del tema (verde principal)
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable(onClick = onLinkClick) // <-- Maneja el evento
        )
    }
}

@Composable
fun RecoveryMethodSwitch(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary, // El color verde principal
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier.clickable(onClick = onClick)
    )
}

// --- Campo de Texto con Icono (IconTextField) ---
@Composable
fun IconTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    iconPainter: Painter,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono a la izquierda
        Icon(
            painter = iconPainter,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary, // Color verde principal
            modifier = Modifier.size(24.dp).padding(end = 8.dp)
        )

        // Campo de texto (usando un TextField simple para la línea inferior)
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant, // Línea gris oscura
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            ),
            // Utiliza un Modifier.weight(1f) para que ocupe el resto del Row
            modifier = Modifier.weight(1f)
        )
    }
}


// --- Tarjeta de Fecha de Monitoreo (DateMonitorCard) ---

@Composable
fun DateMonitorCard(
    date: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Día de monitoreo.",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                date,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

// --- Estructura de datos para los registros ---
data class DailyRecord(
    val date: String,
    val temp: String,
    val humidity: String,
    val length: String,
    val diameter: String,
    val weight: String = "0"
)

// --- Tabla de Registros (DataRecordsTable) ---

@Composable
fun DataRecordsTable(
    records: List<DailyRecord>,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Título
            Text(
                "Últimos 5 días.",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                "Registros",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Encabezado de la Tabla
            RecordRow(
                record = DailyRecord("Fecha", "Temperatura", "Humedad", "Longitud", "Diametro"),
                isHeader = true
            )
            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f), thickness = 1.dp)

            // Filas de Datos
            records.forEachIndexed { index, record ->
                RecordRow(record = record)
                if (index < records.size - 1) {
                    // Solo el divisor horizontal
                    Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f), thickness = 0.5.dp)
                }
            }
        }
    }
}

// --- Fila Individual de la Tabla (Componente auxiliar) ---
@Composable
fun RecordRow(
    record: DailyRecord,
    isHeader: Boolean = false
) {
    val style = if (isHeader) {
        MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
    } else {
        MaterialTheme.typography.bodySmall
    }
    val color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (isHeader) 0.8f else 0.9f)

    // El padding vertical simula la altura de la fila
    val paddingVertical = if (isHeader) 10.dp else 8.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = paddingVertical),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Usamos weight(1f) para que todas las columnas se distribuyan el espacio
        Text(record.date, style = style, color = color, modifier = Modifier.weight(1.5f))
        Text(record.temp, style = style, color = color, modifier = Modifier.weight(1f))
        Text(record.humidity, style = style, color = color, modifier = Modifier.weight(1f))
        Text(record.length, style = style, color = color, modifier = Modifier.weight(1f))
        Text(record.diameter, style = style, color = color, modifier = Modifier.weight(1f))
    }
}

// --- Tarjeta de Predicción (PredictionCard) ---

@Composable
fun PredictionCard(
    title: String,
    predictionResult: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                predictionResult,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary // Color verde principal
                )
            )
        }
    }
}

// --- Componente para los bloques de información (General, Idioma, Cuenta) ---
@Composable
fun ProfileInfoBlock(
    title: String,
    content: @Composable () -> Unit,
    showDivider: Boolean = true
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
    if (showDivider) {
        // Línea divisoria sutil
        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f), thickness = 1.dp)
    }
}

// --- Componente de la Tarjeta Principal del Perfil ---
@Composable
fun ProfileCard(
    username: String,
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Sección superior: Avatar, Nombre y Botón
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar Placeholder
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant) // Color gris claro
                        .border(2.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Avatar de Usuario",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        username,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Botón Editar (reutilizando SecondaryOutlinedButton)
                    SecondaryOutlinedButton(
                        text = "Editar",
                        onClick = onEditClick,
                        modifier = Modifier.fillMaxWidth(0.8f).height(35.dp)
                    )
                }
            }

            // Línea divisoria después del encabezado
            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f), thickness = 1.dp)

            // --- Bloque General ---
            // --- Bloque General ---
            ProfileInfoBlock(
                title = "General",
                content = { // 🟢 CAMBIO: Nombrado explícitamente
                    Text("Nombre", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Descripcion. Lorem in due to core no domeso lasdemon con soldianom de soperta no mina",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
            // --- Bloque Idioma y país ---
            ProfileInfoBlock(
                title = "Idioma y país",
                content = { // 🟢 CAMBIO: Nombrado explícitamente
                    Text("Español", style = MaterialTheme.typography.bodyLarge)
                    Text("México", style = MaterialTheme.typography.bodyLarge)
                }
            )
            // --- Bloque Cuenta (Salir) ---
            ProfileInfoBlock(
                title = "Cuenta",
                content = { // 🟢 LAMBDA PASADO EXPLÍCITAMENTE
                    Text(
                        "Salir",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Red,
                        modifier = Modifier.clickable(onClick = onLogoutClick)
                    )
                },
                showDivider = false
            )
        }
    }
}

// --- Componente de Tarjeta de Gráfico ---
@Composable
fun ChartCard(
    records: List<DailyRecord>, // 🟢 CAMBIO: Aceptar los registros como parámetro
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Título
            Text(
                "Media del peso de racimo a lo largo del tiempo",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // --- GRÁFICA SIMULADA CON DATOS ---
            LineChartSimulation(records = records) // 🟢 Llamada con los datos
            // --- FIN GRÁFICA SIMULADA ---

            Spacer(modifier = Modifier.height(16.dp))

            // Leyendas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Simulación de leyenda
                LegendItem(color = Color(0xFFC8E645), label = "Peso")
                LegendItem(color = Color(0xFF4CAF50), label = "Temperatura")
                LegendItem(color = Color(0xFF2196F3), label = "Humedad")
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, shape = RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// --- Nuevo Componente para Simular el Dibujo del Gráfico ---
@Composable
fun LineChartSimulation(records: List<DailyRecord>) {
    // Los datos deben estar ordenados de más antiguo (izquierda) a más reciente (derecha).
    val reversedRecords = records.reversed()

    // 1. Extracción y Limpieza de datos
    // Usamos .toFloatOrNull() para limpiar las unidades (ºC, %)
    val weights = reversedRecords.map { it.weight.toFloatOrNull() ?: 0f }
    val temperatures = reversedRecords.map { it.temp.replace("ºC", "").toFloatOrNull() ?: 0f }
    val humidities = reversedRecords.map { it.humidity.replace("%", "").toFloatOrNull() ?: 0f }

    // 2. Definición de colores
    val colorPeso = Color(0xFFC8E645) // Amarillo/Verde claro
    val colorTemp = Color(0xFF4CAF50) // Verde
    val colorHumid = Color(0xFF2196F3) // Azul

    // 3. Dibujo del Canvas
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val totalPoints = reversedRecords.size
        if (totalPoints < 2) return@Canvas

        val width = size.width
        val height = size.height
        // Paso horizontal entre cada punto
        val stepX = width / (totalPoints - 1).toFloat()

        // Rango de valores (máximo teórico en el eje Y)
        // Ejemplo: Temperatura 0-30, Humedad 0-100, Peso 0-30. Usamos 100 como base para simplificar la normalización.
        val maxChartValue = 100f

        // Función auxiliar para dibujar una línea de datos
        fun drawDataLine(data: List<Float>, color: Color, scaleFactor: Float = 1f) {
            val path = Path()

            data.forEachIndexed { index, value ->
                // Normalizar y escalar el valor al rango de 0 a maxChartValue
                val scaledValue = value * scaleFactor
                val normalizedY = (scaledValue / maxChartValue).coerceIn(0f, 1f) * height

                // La coordenada Y se invierte (0 en la parte superior, altura en la inferior)
                val y = height - normalizedY
                val x = index * stepX

                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }

            drawPath(
                path = path,
                color = color,
                style = Stroke(width = 4f)
            )
        }

        // Dibujar las tres líneas:
        // Peso (asumo un rango pequeño, factor de 4 para que se vea bien en Y=100)
        drawDataLine(weights, colorPeso, scaleFactor = 4f)

        // Temperatura (asumo un rango pequeño, factor de 4)
        drawDataLine(temperatures, colorTemp, scaleFactor = 4f)

        // Humedad (asumo rango 0-100, factor de 1)
        drawDataLine(humidities, colorHumid, scaleFactor = 1f)

        // Opcional: Ejes
        drawLine(Color.Gray.copy(alpha = 0.5f), Offset(0f, height), Offset(width, height), strokeWidth = 1f)
    }
}

// --- Tarjeta de Cultivo Actual ---
@Composable
fun CropSelectorCard(currentCrop: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Acción para desplegar el selector de cultivo */ }
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "Cultivo actual",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    currentCrop,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.arrow_down), // Asegúrate de tener un recurso para la flecha
                contentDescription = "Seleccionar cultivo",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// --- Tarjeta de Sugerencia ---
@Composable
fun SuggestionCard(suggestionText: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.suggestion_icon),
                contentDescription = "Sugerencia",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    "Sugerencia.",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    suggestionText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// --- Tarjeta de Datos Promedio del Día Anterior ---
@Composable
fun DataAverageCard(
    avgTemp: String,
    avgHumidity: String,
    avgLength: String,
    avgDiameter: String,
    onRegisterClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Datos promedio del día anterior",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Fila de Íconos y Datos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DataColumn(iconId = R.drawable.termometro, label = "Temperatura", value = avgTemp)
                DataColumn(iconId = R.drawable.gota, label = "Humedad", value = avgHumidity)
                DataColumn(iconId = R.drawable.regla, label = "Longitud", value = avgLength)
                DataColumn(iconId = R.drawable.compass, label = "Diámetro", value = avgDiameter)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Icon(
                painter = painterResource(id = R.drawable.leaf_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón "Registrar nuevos datos"
            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Registrar nuevos datos", fontSize = 14.sp)
            }
        }
    }
}

// --- Componente auxiliar para las columnas de datos ---
@Composable
fun DataColumn(iconId: Int, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Icono
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Etiqueta
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        // Valor
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}


@Composable
fun EditProfileHeader(
    headerBackground: Color,
    avatar: androidx.compose.ui.graphics.painter.Painter

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .offset(y = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(headerBackground)
        ) {
            Image(
                painter = painterResource(id = R.drawable.leafs_image), // Reemplaza con tu recurso de patrón
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(headerBackground)
            )

        }

        // Avatar
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center)
                .offset(y = 40.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape) // Borde verde
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = avatar,
                contentDescription = "Avatar de Usuario",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.size(70.dp)
            )
        }

    }

}

@Composable
fun EditableInfoBlock(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }

}