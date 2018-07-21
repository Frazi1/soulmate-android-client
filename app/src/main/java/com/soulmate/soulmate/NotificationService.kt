package com.soulmate.soulmate

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.configuration.interfaces.IConnectionPreferenceManager
import com.soulmate.soulmate.interaction.api.websocket.NotificationWebSocketListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class NotificationService: Service(), KodeinInjected{
    override val injector: KodeinInjector = KodeinInjector()

    private val connectionPreferenceManager: IConnectionPreferenceManager by instance()
    private val okHttpClient: OkHttpClient by instance()
    private val notificationWebSocketHandler: NotificationWebSocketListener by instance()

    private var socket: WebSocket? = null

    private fun startSocket() {
        val req = Request.Builder()
                .url(connectionPreferenceManager.notificationServerUrl)
                .build()
        socket = okHttpClient.newWebSocket(req, notificationWebSocketHandler)
        okHttpClient.dispatcher().executorService().shutdown()
    }
    override fun onCreate() {
        injector.inject(App.globalkodein)
        startSocket()
    }

    override fun onDestroy() {
        socket?.close(1000,"FUCK")
    }

    override fun onBind(intent: Intent?): IBinder {
//        startSocket()
        return Binder()
    }
}