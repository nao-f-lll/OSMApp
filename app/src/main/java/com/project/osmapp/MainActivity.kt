package com.project.osmapp

import android.content.ComponentCallbacks2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.project.osmapp.app.OStoreApp
import com.project.osmapp.logic.AppLifecycleObserver
import com.project.osmapp.logic.AuthUtils
import com.project.osmapp.logic.SessionViewModel
import com.project.osmapp.ui.theme.OSMAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var sessionViewModel: SessionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionViewModel = ViewModelProvider(this).get(SessionViewModel::class.java)

        val lifecycleObserver = AppLifecycleObserver(sessionViewModel, this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)

        enableEdgeToEdge()

        setContent {
            OSMAppTheme {
                OStoreApp()
            }
        }
    }
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            AuthUtils.signOut()
        }
    }
}
// Vista previa de la aplicación
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    OStoreApp()
}
