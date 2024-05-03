package dev.fathony.anvilhelper.base

data class Page(
    val group: PageGroup,
    val name: String,
    val intentBuilder: IntentBuilder,
)
