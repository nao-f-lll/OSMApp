package com.project.osmapp.logic
import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.project.osmapp.R

class AppLifecycleObserver(
    private val viewModel: SessionViewModel,
    private val context: Context
) : DefaultLifecycleObserver {

    companion object {
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001
        const val CHANNEL_ID = "session_logout_channel"
        const val NOTIFICATION_ID = 1
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        viewModel.startSessionTimeout {
            // Después de 2 minutos de inactividad, cerrar sesión y mostrar notificación
            AuthUtils.signOut()
            showLogoutNotification("La sesión se ha cerrado por inactividad en la app.")
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        viewModel.resetSessionTimeout()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        AuthUtils.signOut()
        showLogoutNotification("La sesión se ha cerrado correctamente.")
    }

    private fun showLogoutNotification(message: String) {
        createNotificationChannel()

        // Comprobar si tenemos el permiso para mostrar notificaciones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Si el contexto es una actividad, solicitar el permiso
                if (context is Activity) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        NOTIFICATION_PERMISSION_REQUEST_CODE
                    )
                }
                return
            }
        }
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.main_logo)
            .setContentTitle("Sesión cerrada")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        // Crear el canal de notificación solo para API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Cierre de sesión"
            val descriptionText = "Notificación cuando la sesión se cierra por inactividad"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Registrar el canal con el sistema
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
