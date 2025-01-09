import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.websarva.wings.android.pblapp.R

class MobileMainActivity : AppCompatActivity(), MessageClient.OnMessageReceivedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // メッセージの受信リスナーを登録
        Wearable.getMessageClient(this).addListener(this)
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == "/sensor_data_path") {
            val receivedMessage = String(messageEvent.data)
            Log.d("Mobile Message", "Received data from Wear OS: $receivedMessage")
            // 受け取ったメッセージに対する処理を追加
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // メッセージリスナーを解除
        Wearable.getMessageClient(this).removeListener(this)
    }
}




//package com.websarva.wings.android.pblapp
//
//import android.os.Bundle
//import android.widget.TextView
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.google.android.gms.wearable.DataEvent
//import com.google.android.gms.wearable.DataEventBuffer
//import com.google.android.gms.wearable.DataMapItem
//import com.google.android.gms.wearable.WearableListenerService
//
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        val textView:TextView=findViewById(R.id.acceleratetView)
//    }
//}
////
////class DataLayerListenerService : WearableListenerService() {
////    private var isUIUpdatedOnce = false // UIの更新を一度だけ行うフラグ（確認用）
////
////    override fun onCreate() {
////        super.onCreate()
////        // サービスが開始されたときに、すべての値を0としてUIを初期化（確認用）
////        updateUI(0f, 0f, 0f)
////    }
////
////    override fun onDataChanged(dataEvents: DataEventBuffer) {
////        if (!isUIUpdatedOnce) {//確認用の行
////            for (dataEvent in dataEvents) {
////                if (dataEvent.type == DataEvent.TYPE_CHANGED) {
////                    val dataItem = dataEvent.dataItem
////                    if (dataItem.uri.path == "/accelerometer") {
////                        // データを取り出し、スマートフォンの UI に表示
////                        val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
////                        val sensorX = dataMap.getFloat("sensorX")
////                        val sensorY = dataMap.getFloat("sensorY")
////                        val sensorZ = dataMap.getFloat("sensorZ")
////
////                        // UI に反映するために必要な処理
////                        updateUI(sensorX, sensorY, sensorZ)
////                        isUIUpdatedOnce = true // 一度更新を行ったことを記録（確認用）
////                    }
////                }
////            }
////        }
////    }
////
////    private fun updateUI(sensorX: Float, sensorY: Float, sensorZ: Float) {
////        // メインアクティビティの UI を更新するコードをここに追加
////
////    }
////}
