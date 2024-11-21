package com.websarva.wings.android.pblapp.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class WearBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // インテントのアクションをチェックして処理を行う
        when (intent.action) {
            "com.example.broadcast.MY_NOTIFICATION1" -> {
                val data = intent.getStringExtra("data")
                Toast.makeText(context, "Received MY_NOTIFICATION1: $data", Toast.LENGTH_SHORT).show()
                Log.d("MyBroadcastReceiver", "Received MY_NOTIFICATION1 with data: $data")
            }
            "com.example.broadcast.MY_NOTIFICATION2" -> {
                val data = intent.getStringExtra("data")
                Toast.makeText(context, "Received MY_NOTIFICATION2: $data", Toast.LENGTH_SHORT).show()
                Log.d("MyBroadcastReceiver", "Received MY_NOTIFICATION2 with data: $data")
            }
            else -> {
                Log.d("MyBroadcastReceiver", "Unknown broadcast action received: ${intent.action}")
            }
        }
    }
}
