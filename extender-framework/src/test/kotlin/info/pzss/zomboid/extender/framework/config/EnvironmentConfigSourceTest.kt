package info.pzss.zomboid.extender.framework.config

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EnvironmentConfigSourceTest {
    private val testConfigSource = EnvironmentConfigSource(
        mapOf(
            "SANDBOX__TIME_OF_DAY" to "50",
            "RCON_PORT" to "1234",
            "ZOMBIE_SPAWN_RATE" to "10"
        )
    )

    @Test
    fun `dot separators are fixed`() {
        assertEquals("50", testConfigSource["Sandbox.TimeOfDay"])
    }

    @Test
    fun `camel case options to uppercase`() {
        assertEquals("1234", testConfigSource["RCONPort"])
    }

    @Test
    fun `consecutive uppercase preserved`() {
        assertEquals("10", testConfigSource["ZombieSpawnRate"])
    }
}