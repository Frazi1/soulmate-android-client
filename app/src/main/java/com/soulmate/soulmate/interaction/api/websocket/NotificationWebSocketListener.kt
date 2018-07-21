package com.soulmate.soulmate.interaction.api.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.soulmate.shared.dtos.notifications.DialogNotificationDto
import com.soulmate.soulmate.App
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.configuration.interfaces.ILogger
import com.soulmate.soulmate.presentation.notifications.NotificationHelper
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class NotificationWebSocketListener(private val logger: ILogger,
                                    private val errorHandler: IErrorHandler,
                                    private val mapper: ObjectMapper,
                                    private val notificationHelper: NotificationHelper) : WebSocketListener() {
    override fun onMessage(webSocket: WebSocket, text: String?) {
        logger.info(text ?: "")
        text?.let {
            notificationHelper.showNewMessageNotification(App.instance.applicationContext, mapper.readValue(text, DialogNotificationDto::class.java))
        }
    }

    override fun onOpen(webSocket: WebSocket?, response: Response?) {
        logger.info("Socket opened!" + "\n" + response?.body()?.string())
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
        logger.error(t?.message ?: "Error creating socket")
    }
}