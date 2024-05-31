package dev.fathony.anvilhelper.common

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class RandomNumberProvider @Inject constructor() : NumberProvider {
    override fun provideNumber(): Int {
        return Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
    }
}
