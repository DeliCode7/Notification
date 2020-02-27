package ru.otus.demo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(this@MainActivity, 0, intent, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Channel name" // getString(R.string.channel_name)
                val description = "Channel description" //getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                channel.description = description
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager!!.createNotificationChannel(channel)
            }
            val builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_details_24)
                    .setContentTitle("My notification")
                    .setContentText("Hello World!")
                    .setPriority(NotificationCompat.PRIORITY_LOW) // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)

                    /*.addAction(R.drawable.baseline_details_24, "Click me", pendingIntent)*/
                    /* .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources,
                            R.drawable.sq500x500))
                            .setSummaryText("Summary"))*/
                    /*.setStyle(NotificationCompat.BigTextStyle().bigText(resources.getString(R.string.lorem_ipsum)))*/
                    .setAutoCancel(true)
            val notificationManager = NotificationManagerCompat.from(this@MainActivity)
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(2, builder.build())
        }
    }



    companion object {
        const val CHANNEL_ID = "channel"
    }
}