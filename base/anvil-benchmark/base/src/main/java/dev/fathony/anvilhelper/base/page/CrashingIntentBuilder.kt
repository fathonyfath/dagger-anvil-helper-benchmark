package dev.fathony.anvilhelper.base.page

import android.content.Context
import android.content.Intent

class CrashingIntentBuilder : IntentBuilder() {
    override fun create(context: Context): Intent {
        throw NotImplementedError("Using ${this::class.simpleName} that throw Error.")
    }
}
