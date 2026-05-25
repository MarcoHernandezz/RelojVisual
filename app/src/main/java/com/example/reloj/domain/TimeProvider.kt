package com.example.reloj.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant

/**
 * Motor de tiempo que emite pulsos cada segundo.
 */
interface TimeProvider {
    fun getTimeTicks(): Flow<Instant>
}

class SystemTimeProvider : TimeProvider {
    override fun getTimeTicks(): Flow<Instant> = flow {
        while (true) {
            emit(Instant.now())
            delay(1000)
        }
    }
}
