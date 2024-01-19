package com.example.imagenes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Composable que representa la vista de pestañas. Muestra pestañas para "Galería", "Cámara" y "Colección".
 * Permite cambiar entre las diferentes vistas correspondientes a cada pestaña.
 *
 * @param viewModel ViewModel que contiene la lógica de la funcionalidad de escaneo de texto.
 */
@Composable
fun TabsView(viewModel: ScannerViewModel) {
    // Estado para mantener la pestaña actualmente seleccionada
    var selectedTab by remember { mutableStateOf(0) }

    // Lista de nombres de pestañas
    val tabs = listOf("Galería", "Cámara", "Colección")

    // Columna principal para la disposición de la interfaz de usuario
    Column {
        // Fila de pestañas con indicador y estilo personalizado
        TabRow(
            selectedTabIndex = selectedTab,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab])
                )
            }
        ) {
            // Crear pestañas a partir de la lista de nombres
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(text = title) }
                )
            }
        }

        // Mostrar la vista correspondiente a la pestaña seleccionada
        when (selectedTab) {
            0 -> GalleryView(viewModel) // Vista de la Galería
            1 -> CameraView(viewModel) // Vista de la Cámara
            2 -> CollectionGalleryView(viewModel) // Vista de la Colección
        }
    }
}
