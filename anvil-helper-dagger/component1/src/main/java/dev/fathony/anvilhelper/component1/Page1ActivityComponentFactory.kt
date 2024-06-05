package dev.fathony.anvilhelper.component1

import dev.fathony.anvilhelper.base.dagger.DaggerSubcomponentFactory

interface Page1ActivityComponentFactory : DaggerSubcomponentFactory {
    fun createPage1ActivityComponent(activity: Page1Activity): Page1ActivityComponent
}
