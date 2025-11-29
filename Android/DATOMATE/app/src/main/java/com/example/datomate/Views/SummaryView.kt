package com.example.datomate.Views

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datomate.Components.*
import com.example.datomate.Navigation.Screen
import com.example.datomate.R
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.flex.FlexDelegate
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryView(navController: NavController) {

    var currentScreen by remember { mutableStateOf(Screen.Summary.route) }
    val context = LocalContext.current

    // Estado para el resultado de la predicción
    var resultadoTexto by remember { mutableStateOf("Calculando...") }

    // --- TUS DATOS DE ENTRADA (Hardcoded por ahora) ---
    val datosDeEntrada = remember {
        arrayOf(
            floatArrayOf(17.802535f, 89.383403f, 19.555556f, 8.270476f), // [0] Día 1
            floatArrayOf(18.386319f, 80.709757f, 22.0f, 9.21381f),       // [1] Día 2
            floatArrayOf(18.611007f, 79.638993f, 22.0f, 9.21381f),       // [2] Día 3
            floatArrayOf(18.077951f, 79.542153f, 22.0f, 9.21381f),       // [3] Día 4
            floatArrayOf(18.404073f, 75.385927f, 22.0f, 9.21381f)        // [4] Día 5
        )
    }

    // Ejecutar predicción automática al abrir la pantalla usando el Helper
    LaunchedEffect(Unit) {
        try {
            val modelo = TomatoModelHelper(context)
            val kilos = modelo.predecir(datosDeEntrada)
            resultadoTexto = "Peso del racimo: %.2f kg".format(kilos)
            modelo.close()
        } catch (e: Exception) {
            e.printStackTrace()
            resultadoTexto = "Error al predecir"
        }
    }

    val navItems = listOf(
        BottomNavItem(R.drawable.home, Screen.Home.route),
        BottomNavItem(R.drawable.captura, Screen.DataEntry.route),
        BottomNavItem(R.drawable.prediccion, Screen.Summary.route),
        BottomNavItem(R.drawable.reportes, Screen.Reports.route),
        BottomNavItem(R.drawable.imagenes, "image"),
        BottomNavItem(R.drawable.perfil, Screen.Profile.route)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { AppLogoBar(100, Alignment.TopStart) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavBar(
                items = navItems,
                currentRoute = currentScreen,
                onItemSelected = { route ->
                    currentScreen = route
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
        ) {
            // Pasamos 'resultadoTexto' y el arreglo 'datosDeEntrada' a la vista
            ContenteSummaryView(
                prediccion = resultadoTexto,
                datos = datosDeEntrada
            )
        }
    }
}

@Composable
fun ContenteSummaryView(prediccion: String, datos: Array<FloatArray>){

    // Aquí llenamos la lista con los datos reales del arreglo
    val sampleRecords = listOf(
        // --- Fila 1: Datos del Día 5 (Index 4) ---
        DailyRecord(
            "19/11/25",
            "%.1fºC".format(datos[4][0]),
            "%.0f%%".format(datos[4][1]),
            "%.1f cm".format(datos[4][2]),
            "%.1f cm".format(datos[4][3])
        ),
        // --- Fila 2: Datos del Día 4 (Index 3) ---
        DailyRecord(
            "18/11/25",
            "%.1fºC".format(datos[3][0]),
            "%.0f%%".format(datos[3][1]),
            "%.1f cm".format(datos[3][2]),
            "%.1f cm".format(datos[3][3])
        ),
        // --- Fila 3: Datos del Día 3 (Index 2) ---
        DailyRecord(
            "17/11/25",
            "%.1fºC".format(datos[2][0]),
            "%.0f%%".format(datos[2][1]),
            "%.1f cm".format(datos[2][2]),
            "%.1f cm".format(datos[2][3])
        ),
        // --- Fila 4: Datos del Día 2 (Index 1) ---
        DailyRecord(
            "16/11/25",
            "%.1fºC".format(datos[1][0]),
            "%.0f%%".format(datos[1][1]),
            "%.1f cm".format(datos[1][2]),
            "%.1f cm".format(datos[1][3])
        ),
        // --- Fila 5: Datos del Día 1 (Index 0) ---
        DailyRecord(
            "15/11/25",
            "%.1fºC".format(datos[0][0]),
            "%.0f%%".format(datos[0][1]),
            "%.1f cm".format(datos[0][2]),
            "%.1f cm".format(datos[0][3])
        )
    )

    // Tarjeta de Registros
    DataRecordsTable(
        records = sampleRecords,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    // Tarjeta de Predicción
    PredictionCard(
        title = "Predicción del siguiente día:",
        predictionResult = prediccion
    )
}

// --- CLASE HELPER TFLITE (VITAL PARA QUE FUNCIONE LA IA) ---
class TomatoModelHelper(context: Context) {
    private var interpreter: Interpreter? = null

    init {
        val options = Interpreter.Options()
        // Habilita operaciones complejas (necesario para LSTMs o modelos avanzados)
        options.addDelegate(FlexDelegate())

        // Cargar modelo
        val modelFile = loadModelFile(context, "model_tomates.tflite")
        interpreter = Interpreter(modelFile, options)
    }

    fun predecir(datos: Array<FloatArray>): Float {
        // Ajusta esto según la forma de entrada de tu modelo (Input Shape)
        val input = arrayOf(datos) // [1, 5, 4]
        val output = Array(1) { FloatArray(1) } // [1, 1]
        interpreter?.run(input, output)
        return output[0][0]
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
    }

    fun close() {
        interpreter?.close()
    }
}