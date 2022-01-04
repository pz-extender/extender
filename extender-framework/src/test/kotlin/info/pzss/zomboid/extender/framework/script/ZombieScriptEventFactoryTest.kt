package info.pzss.zomboid.extender.framework.script

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import info.pzss.zomboid.extender.framework.script.event.ZomboidScriptEventFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestEvent(val test: String, val test2: Int) : ZomboidScriptEvent
class NoArgsEvent : ZomboidScriptEvent

internal class ZombieScriptEventFactoryTest {
    @Test
    fun `varargs are passed to event constructor`() {
        val factory = ZomboidScriptEventFactory.fromOnlyConstructor(TestEvent::class.java)
        val event = factory.create("TestEvent", arrayOf("test", 1))

        assertEquals("test", event.test)
        assertEquals(1, event.test2)
    }

    @Test

    fun `wrong arg types raises error`() {
        val factory = ZomboidScriptEventFactory.fromOnlyConstructor(TestEvent::class.java)

        assertThrows<ClassCastException> {
            factory.create("TestEvent", arrayOf(1, "test"))
        }
    }

    @Test
    fun `zero arg events are constructed`() {
        val factory = ZomboidScriptEventFactory.fromOnlyConstructor(NoArgsEvent::class.java)
        factory.create("NoArgsEvent", emptyArray())
    }
}