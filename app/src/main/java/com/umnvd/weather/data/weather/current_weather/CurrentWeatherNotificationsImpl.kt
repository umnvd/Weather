package com.umnvd.weather.data.weather.current_weather

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.umnvd.weather.MainActivity
import com.umnvd.weather.R
import com.umnvd.weather.models.CurrentWeather
import javax.inject.Inject


class CurrentWeatherNotificationsImpl @Inject constructor(
    private val context: Context
): CurrentWeatherNotifications {

    private val manager = NotificationManagerCompat.from(context)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                manager.createNotificationChannel(
                    NotificationChannelCompat.Builder(
                        CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_HIGH
                    )
                        .setName(context.getString(R.string.current_weather_channel))
                        .setDescription(context.getString(R.string.current_weather_channel_description))
                        .build()
                )
            }
        }

    }

    override fun show(currentWeather: CurrentWeather) {
        val pendingIntent = PendingIntent.getActivity(
            context, REQUEST_CONTENT,
            Intent(context, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
        )

        val iconBitmap = Glide.with(context)
            .asBitmap()
            .load(currentWeather.iconUrl)
            .submit()
            .get()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_weather)
            .setContentTitle(currentWeather.cityName)
            .setContentText(context.getString(R.string.value_in_degrees, currentWeather.temp))
            .setLargeIcon(iconBitmap)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .build()

        manager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification)
    }

    companion object {

        private const val CHANNEL_ID = "currentWeatherChannel"
        private const val REQUEST_CONTENT = 101
        private const val NOTIFICATION_TAG = "currentWeather"
        private const val NOTIFICATION_ID = 322

    }

}