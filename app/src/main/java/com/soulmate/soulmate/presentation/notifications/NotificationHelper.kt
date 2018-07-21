package com.soulmate.soulmate.presentation.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationCompat.PRIORITY_MAX
import android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC
import android.support.v4.app.NotificationManagerCompat
import com.soulmate.shared.dtos.notifications.DialogNotificationDto
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.activity.PrivateChatActivity

class NotificationHelper {
    companion object {
        const val ALL_CHANNEL_ID = "all"
    }
    fun showNewMessageNotification(context: Context, dialogNotificationDto: DialogNotificationDto) {
        val builder = NotificationCompat.Builder(context, ALL_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(dialogNotificationDto.dialogName)
                .setContentText(dialogNotificationDto.messageDto.content)
                .setPriority(PRIORITY_MAX)
                .setVisibility(VISIBILITY_PUBLIC)
        val privateChatIntent = PrivateChatActivity.getIntent(context)
        privateChatIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        privateChatIntent.putExtra("ids", arrayOf(dialogNotificationDto.dialogId))

        val pendingIntend = PendingIntent.getActivity(context, 0 ,privateChatIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntend)

        NotificationManagerCompat.from(context)
                .notify(1, builder.build())
    }
}