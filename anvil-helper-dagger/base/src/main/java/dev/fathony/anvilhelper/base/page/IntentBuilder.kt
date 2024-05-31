package dev.fathony.anvilhelper.base.page

import android.content.Context
import android.content.Intent

abstract class IntentBuilder {

    abstract fun create(context: Context): Intent
}
