package com.websarva.wings.android.pblapp.presentation

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable
import com.websarva.wings.android.pblapp.R

class MainActivity : AppCompatActivity(), DataClient.OnDataChangedListener {

    private lateinit var sensorDataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TextViewの初期化
        sensorDataTextView = findViewById(R.id.sensorDataTextView)

        // 初回起動時にIntentからデータを取得（あれば）
        intent?.getStringExtra("sensor_data")?.let { data ->
            updateSensorData(data)
        }
    }

    override fun onResume() {
        super.onResume()
        // DataClientリスナーを登録
        Wearable.getDataClient(this).addListener(this)
    }

    override fun onPause() {
        super.onPause()
        // DataClientリスナーを解除
        Wearable.getDataClient(this).removeListener(this)
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED) {
                val path = event.dataItem.uri.path
                if (path == "/sensor_data") { // Wear OSから送られるデータのパス
                    val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                    val sensorData = dataMap.getString("sensor_data") // データを取得

                    Log.d("MainActivity", "Received sensor data: $sensorData")

                    // UIを更新
                    runOnUiThread {
                        if (sensorData != null) {
                            updateSensorData(sensorData)
                        }
                    }
                }
            }
        }
    }

    private fun updateSensorData(data: String) {
        sensorDataTextView.text = data
    }
}
