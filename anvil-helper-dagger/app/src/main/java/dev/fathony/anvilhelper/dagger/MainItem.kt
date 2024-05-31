package dev.fathony.anvilhelper.dagger

import dev.fathony.anvilhelper.base.page.Page
import dev.fathony.anvilhelper.base.page.PageGroup

sealed class MainItem {

    data class Header(val group: PageGroup) : MainItem()
    data class Item(val page: Page) : MainItem()
}
