package com.example.simulacroglobalmuultimedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class MainActivityViewModel: ViewModel() {

    var  usuariosResponse= UsuariosResponse(mutableListOf())

    private val _isVisible by lazy { MediatorLiveData<Boolean>() }//_que sea privada y que no sea accesible lazy si no la usas no se crea la variable
    val isVisible: LiveData<Boolean>
        get() = _isVisible

    private val _responseUsuarios by lazy { MediatorLiveData<UsuariosResponse>() }
    val responseUsuarios: LiveData<UsuariosResponse>
        get() = _responseUsuarios

    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText: LiveData<String>
        get() = _responseText

    suspend fun setResponseTextInMainThread(value: String) = withContext(Dispatchers.Main) {
        _responseText.value = value
    }

    suspend fun setIsVisibleInMainThread(value: Boolean) = withContext(Dispatchers.Main) {
        _isVisible.value = value
    }

    suspend fun setResponseUsuarioInMainThread(usuariosResponse: UsuariosResponse) = withContext(Dispatchers.Main) {
        _responseUsuarios.value =usuariosResponse //mandarlo al hilo principal
        }

    fun getUsuarios() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)

                val client = OkHttpClient()

                val request = Request.Builder()
                request.url("http://randomuser.me/api/?results=100")


                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            setResponseTextInMainThread("Algo ha ido mal")
                            setIsVisibleInMainThread(false)
                        }

                    }

                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->
                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()

                             usuariosResponse = gson.fromJson(body, UsuariosResponse::class.java)

                            println(usuariosResponse)


                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                setResponseUsuarioInMainThread(usuariosResponse)
                                setIsVisibleInMainThread(false)
                            }
                        }
                    }
                })
            }
        }
    }
    fun obtenerUsuarios() {
        CoroutineScope(Dispatchers.Main).launch {
            setResponseUsuarioInMainThread(usuariosResponse)
        }
    }
    fun obtenerMasculino(){
        CoroutineScope(Dispatchers.Main).launch {
            val usuariosResponse= UsuariosResponse(usuariosResponse.results.filter { it.gender == "male" })
            setResponseUsuarioInMainThread(usuariosResponse)
        }

    }fun obtenerFemenino(){
        CoroutineScope(Dispatchers.Main).launch {
            val usuariosResponse= UsuariosResponse(usuariosResponse.results.filter { it.gender == "female" })
            setResponseUsuarioInMainThread(usuariosResponse)
        }

    }


}




