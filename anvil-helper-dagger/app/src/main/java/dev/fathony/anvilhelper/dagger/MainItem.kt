package dev.fathony.anvilhelper.dagger

import dev.fathony.anvilhelper.base.Page
import dev.fathony.anvilhelper.base.PageGroup

sealed class MainItem {

    data class Header(val group: PageGroup) : MainItem()
    data class Item(val page: Page) : MainItem()
}
