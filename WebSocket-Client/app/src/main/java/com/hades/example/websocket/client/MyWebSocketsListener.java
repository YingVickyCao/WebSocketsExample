package com.hades.example.websocket.client;

import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MyWebSocketsListener extends WebSocketListener {
    private static final String TAG = "MyWebSocketsListener";
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private IMessageView messageView;

    public MyWebSocketsListener(IMessageView messageView) {
        this.messageView = messageView;
    }

    public void setMessageView(IMessageView messageView) {
        this.messageView = messageView;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        Log.d(TAG, "onOpen: ");
        webSocket.send("Hi, 123,中国");
        webSocket.send(ByteString.decodeHex("deadbeef"));
        webSocket.close(NORMAL_CLOSURE_STATUS, "Bye");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        output("Receiving onMessage string: " + text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        output("Receiving onMessage bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        output("Closing : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        output("Error : " + t.getMessage());
    }

    private void output(final String txt) {
        Log.d(TAG, txt);
        if (null != messageView) {
            messageView.message(txt);
        }
    }
}
