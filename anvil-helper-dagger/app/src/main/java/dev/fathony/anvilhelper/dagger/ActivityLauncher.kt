package dev.fathony.anvilhelper.dagger

import android.content.Intent

interface ActivityLauncher {
    
    fun launchActivity(intent: Intent)
}
