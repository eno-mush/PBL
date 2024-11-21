package com.websarva.wings.android.pblapp.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.websarva.wings.android.pblapp.R

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accSensor: Sensor? = null
    private var sensorX = 1
    private var sensorY = 2
    private var sensorZ = 3

    // BroadcastReceiver のインスタンス
    private val myBroadcastReceiver = WearBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        if (accSensor == null) {
            Log.e("com.websarva.wings.android.pblapp.presentation.MainActivity", "加速度センサーが見つかりません")
            val startTextView: TextView = findViewById(R.id.startView)


            startTextView.text = "加速度センサーが見つかりません"
        }

        //ブロードキャストtest ボタン
        val testButton:Button=findViewById(R.id.upButton)
        testButton.setOnClickListener {
            // インテントをブロードキャスト
            val intent = Intent("com.example.broadcast.MY_NOTIFICATION1")
            intent.putExtra("data", "Notice me senpai!")
            Log.d("MainActivity", "Broadcast MY_NOTIFICATION1 sent with data: Notice me senpai!")
            sendBroadcast(intent)
        }

        // BroadcastReceiver の登録
        val intentFilter = IntentFilter().apply {
            addAction("com.example.broadcast.MY_NOTIFICATION1")
            addAction("com.example.broadcast.MY_NOTIFICATION2")
        }
        registerReceiver(myBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED)
    }

    override fun onResume() {
        super.onResume()
        accSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) // リスナー登録
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this) // リスナー解除
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
                val sensorX: Float = it.values[0]
                val sensorY: Float = it.values[1]
                val sensorZ: Float = it.values[2]

                // TextView を取得して数値を表示する
                val startTextView: TextView = findViewById(R.id.startView)
                startTextView.text = "X: ${sensorX}\nY: ${sensorY}\nZ: ${sensorZ}"

//                // ブロードキャストの送信
//                val intent = Intent("com.example.broadcast.MY_NOTIFICATION1")
//                intent.putExtra("data","X: ${sensorX}\nY: ${sensorY}\nZ: ${sensorZ}")
//                sendBroadcast(intent) // ブロードキャストを送信
//
//                Log.d("Wear Broadcast","X: ${sensorX}\nY: ${sensorY}\nZ: ${sensorZ}")
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 必要に応じて処理を追加
    }
}
