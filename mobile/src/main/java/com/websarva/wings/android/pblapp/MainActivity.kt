package com.websarva.wings.android.pblapp

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // BroadcastReceiver のインスタンス
    private val myBroadcastReceiver = MyBroadcastReceiver()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ボタン1配置
        val button1: Button = findViewById(R.id.button1)
        button1.setOnClickListener {
            // インテントをブロードキャスト
            val intent = Intent("com.example.broadcast.MY_NOTIFICATION1")
            intent.putExtra("data", "Notice me senpai!")
            Log.d("MainActivity", "Broadcast MY_NOTIFICATION1 sent with data: Notice me senpai!")
            sendBroadcast(intent)
        }

        // ボタン2配置
        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener {
            // インテントをブロードキャスト
            val intent = Intent("com.example.broadcast.MY_NOTIFICATION2")
            intent.putExtra("data", "Hello, world!")
            Log.d("MainActivity", "Broadcast MY_NOTIFICATION2 sent with data: Hello, world!")
            sendBroadcast(intent)
        }

        // BroadcastReceiver の登録
        val intentFilter = IntentFilter().apply {
            addAction("com.example.broadcast.MY_NOTIFICATION1")
            addAction("com.example.broadcast.MY_NOTIFICATION2")
        }
        registerReceiver(myBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED)

//        registerReceiver(myBroadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
        Log.d("MainActivity", "BroadcastReceiver registered")
    }

    override fun onDestroy() {
        super.onDestroy()
        // BroadcastReceiver の解除
        try {
            unregisterReceiver(myBroadcastReceiver)
            Log.d("MainActivity", "BroadcastReceiver unregistered")
        } catch (e: IllegalArgumentException) {
            Log.e("MainActivity", "Receiver not registered or already unregistered: ${e.message}")
        }
    }
}
