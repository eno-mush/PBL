import com.google.android.gms.wearable.Wearable
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.Node
import android.content.Context
import android.util.Log

fun sendSensorDataToPhone(context: Context, data: String) {
    val messageClient = Wearable.getMessageClient(context)

    // すべての接続されたノード（デバイス）を取得
    val nodeListTask: Task<List<Node>> = Wearable.getNodeClient(context).connectedNodes
    nodeListTask.addOnSuccessListener { nodes ->
        for (node in nodes) {
            val path = "/sensor_data_path" // 送信するパス（任意）
            val messagePayload = data.toByteArray() // 送信するデータをバイト配列に変換

            // メッセージを送信
            messageClient.sendMessage(node.id, path, messagePayload)
                .addOnSuccessListener {
                    Log.d("Wearable Message", "Message sent successfully to ${node.displayName}")
                }
                .addOnFailureListener { e ->
                    Log.e("Wearable Message", "Failed to send message", e)
                }
        }
    }
}
