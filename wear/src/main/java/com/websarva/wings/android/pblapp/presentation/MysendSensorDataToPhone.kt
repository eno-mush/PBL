package com.websarva.wings.android.pblapp.presentation

import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import android.content.Context
import android.util.Log

fun sendSensorDataToPhone(context: Context, data: String) {
    // Data Layer APIのPutDataMapRequestを作成
    val dataMapRequest = PutDataMapRequest.create("/sensor_data")
    dataMapRequest.dataMap.putString("sensor_data", data)
    dataMapRequest.dataMap.putLong("timestamp", System.currentTimeMillis()) // タイムスタンプを追加

    // データを送信
    val putDataRequest = dataMapRequest.asPutDataRequest()
    putDataRequest.setUrgent() // リアルタイム性を高めるためUrgentに設定
    val dataClient = Wearable.getDataClient(context)

    // 非同期で送信
    dataClient.putDataItem(putDataRequest)
        .addOnSuccessListener {
            Log.d("sendSensorDataToPhone", "Data sent successfully: $data")
        }
        .addOnFailureListener { e ->
            Log.e("sendSensorDataToPhone", "Failed to send data", e)
        }
}
