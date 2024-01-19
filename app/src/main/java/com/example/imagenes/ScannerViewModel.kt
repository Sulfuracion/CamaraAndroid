package com.example.imagenes

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

/**
 * ViewModel para la funcionalidad de escaneo de texto.
 */
class ScannerViewModel : ViewModel() {

    // Estado mutable para almacenar el texto reconocido
    var recognizedText by mutableStateOf("")
        private set

    /**
     * Limpia el texto reconocido.
     */
    fun cleanText() {
        recognizedText = ""
    }

    /**
     * Procesa el texto reconocido a partir de una imagen proporcionada.
     *
     * @param text     La URI de la imagen a procesar.
     * @param context  El contexto de la aplicación.
     */
    fun onRecognizedText(text: Any?, context: Context) {
        var image: InputImage? = null
        try {
            // Intenta crear una instancia de InputImage a partir de la URI de la imagen
            image = InputImage.fromFilePath(context, text as Uri)
        } catch (e: IOException) {
            // Maneja excepciones relacionadas con la lectura de la imagen
            e.printStackTrace()
        }

        // Procesa la imagen si la instancia de InputImage es válida
        image?.let {
            // Utiliza la API de reconocimiento de texto para procesar la imagen
            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS).process(it)
                .addOnSuccessListener { text ->
                    // Actualiza el estado con el texto reconocido
                    recognizedText = text.text
                }.addOnFailureListener {
                    // Muestra un mensaje de error en caso de fallo
                    showToast(context, "Error al leer la imagen")
                }
        }
    }

    /**
     * Muestra un mensaje Toast en la aplicación.
     *
     * @param context  El contexto de la aplicación.
     * @param message  El mensaje a mostrar.
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}