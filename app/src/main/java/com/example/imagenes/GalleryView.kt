package com.example.imagenes

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


/**
 * Composable que representa la vista de la galería, permite seleccionar una imagen de la galería
 * y muestra el texto reconocido de la imagen junto con la capacidad de copiar el texto al portapapeles.
 *
 * @param viewModel ViewModel que contiene la lógica de la funcionalidad de escaneo de texto.
 */
@Composable
fun GalleryView(viewModel: ScannerViewModel) {
    // Obtener el contexto local y el gestor de portapapeles local
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

    // Estado para almacenar la imagen seleccionada, inicializado con una imagen predeterminada
    var image: Any? by remember { mutableStateOf(R.drawable.gallery) }

    // Inicializar el launcher para seleccionar imágenes de la galería
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            // Actualizar la imagen y procesar el texto reconocido
            image = it
            viewModel.onRecognizedText(image, context)
        } else {
            // Mostrar un mensaje si no se selecciona ninguna imagen
            viewModel.showToast(context, "No se ha seleccionado ninguna imagen")
        }
    }

    // Diseño de la columna principal
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mostrar la imagen seleccionada, permitiendo la selección de nuevas imágenes
        Image(
            modifier = Modifier
                .clickable {
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
                .padding(16.dp, 8.dp),
            painter = rememberAsyncImagePainter(image),
            contentDescription = null
        )

        // Espaciador vertical
        Spacer(modifier = Modifier.height(25.dp))

        // Estado de desplazamiento para el texto reconocido
        val scrollState = rememberScrollState()

        // Mostrar el texto reconocido, permitiendo el desplazamiento y la copia al portapapeles
        Text(
            text = viewModel.recognizedText,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(scrollState)
                .clickable {
                    // Copiar texto al portapapeles y mostrar un mensaje
                    clipboard.setText(AnnotatedString(viewModel.recognizedText))
                    viewModel.showToast(context, "Copiado")
                }
        )
    }
}
