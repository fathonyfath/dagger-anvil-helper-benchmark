package dev.fathony.anvilhelper.base

import android.content.Context
import android.os.Bundle

abstract class IntentBuilder {

    abstract fun create(context: Context): Bundle
}
