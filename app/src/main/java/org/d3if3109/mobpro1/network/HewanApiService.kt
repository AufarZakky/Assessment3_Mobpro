package org.d3if3109.mobpro1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.d3if3109.mobpro1.model.Hewan
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import okhttp3.logging.HttpLoggingInterceptor
import org.d3if3109.mobpro1.model.OpStatus

private const val BASE_URL = "http://20.2.250.135/"

val logging = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

val httpClient = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(httpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



interface HewanApiService {
    @GET("file-upload/{upload_by}")
    suspend fun getHewan(@Path("upload_by") uploadBy: String): OpStatus<List<Hewan>>

    @Multipart
    @POST("file-upload")
    suspend fun postHewan(
        @Part file: MultipartBody.Part,
        @Part("upload_by") uploadBy: RequestBody,
        @Part("description") description: RequestBody,
    ): OpStatus<Hewan>

    @DELETE("file-upload/{id}")
    suspend fun deleteHewan(@Path("id") id: Int): OpStatus<Hewan>
// Gunakan Unit jika tidak ada data yang dikembalikan
}



object HewanApi {
    val service: HewanApiService by lazy {
        retrofit.create(HewanApiService::class.java)
    }
}

enum class ApiStatus {LOADING, SUCCESS, FAILED}