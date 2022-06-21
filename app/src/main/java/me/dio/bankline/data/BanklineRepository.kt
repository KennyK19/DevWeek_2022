package me.dio.bankline.data

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import me.dio.bankline.data.State.Error
import me.dio.bankline.data.remote.BanklineApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BanklineRepository {

    private val TAG = javaClass.simpleName

    private val restApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.64:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BanklineApi::class.java)
    }

    fun findBankStatement(accountHolderId: Int) = liveData{
        emit(State.Wait)
        try {
            emit( State.Success(data = restApi.findBankStatement(accountHolderId)))
        } catch (e: Exception){
            Log.e(TAG, e.message, e)
            emit(State.Error(e.message))
        }
    }
}