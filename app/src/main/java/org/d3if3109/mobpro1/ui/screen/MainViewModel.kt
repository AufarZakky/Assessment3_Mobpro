package org.d3if3109.mobpro1.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if3109.mobpro1.model.Hewan
import org.d3if3109.mobpro1.network.ApiStatus
import org.d3if3109.mobpro1.network.HewanApi
import java.io.ByteArrayOutputStream

class MainViewModel: ViewModel() {

    var data = mutableStateOf(emptyList<Hewan>())
        private set
    var status = MutableStateFlow(ApiStatus.LOADING)
        private set
    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = HewanApi.service.getHewan(userId).data
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
    fun saveData(userId: String, description: String, bitmap: Bitmap){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val result = HewanApi.service.postHewan(
                    bitmap.toMultipartBody(),
                    userId.toRequestBody("text/plain".toMediaTypeOrNull()),
                    description.toRequestBody("text/plain".toMediaTypeOrNull()),
                )

                if (result.statusCode == 201 )
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            }catch (e: Exception) {
                Log.d("MainViewModel.saveData", e.toString())
                Log.d("MainViewModel.saveData", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteData(userEmail: String, imageId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = HewanApi.service.deleteHewan(imageId,)
                if (result.statusCode == 200) retrieveData(userEmail)
                else throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("MainViewModel.deleteData", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    private  fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "images/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "image", "image.jpg", requestBody
        )
    }

    fun clearMessage() {errorMessage.value = null}
}