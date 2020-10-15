package com.hades.example.websocket.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity implements IMessageView {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MyWebSocketsListener myWebSocketsListener;

    private TextView message;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebSocketsListener = new MyWebSocketsListener(this);
        findViewById(R.id.connect).setOnClickListener(v -> connect());
        findViewById(R.id.disconnect).setOnClickListener(v -> disconnect());
        message = findViewById(R.id.message);

        client = new OkHttpClient();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != myWebSocketsListener) {
            myWebSocketsListener.setMessageView(null);
        }
    }

    private void connect() {
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        /**
         * ERROR:java.util.concurrent.RejectedExecutionException: Task okhttp3.RealCall$AsyncCall@551af95 rejected from java.util.concurrent.ThreadPoolExecutor@98558aa[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 1]
         */
        WebSocket ws = client.newWebSocket(request, myWebSocketsListener);

        client.dispatcher().executorService().shutdown();
    }

    private void disconnect() {
    }

    @Override
    public void message(String text) {
        Log.d(TAG, "message: " + text);
        runOnUiThread(() -> message.setText(message.getText().toString() + text + "\n"));
    }
}