package info.pzss.zomboid.extender.framework

import ch.qos.logback.classic.LoggerContext
import info.pzss.zomboid.extender.framework.script.event.ZomboidScriptEventDispatcher
import org.slf4j.LoggerFactory


class ZomboidExtensionContext(val eventDispatcher: ZomboidScriptEventDispatcher) {
    fun release() {
        (LoggerFactory.getILoggerFactory() as? LoggerContext)?.stop()
    }

    companion object {
        lateinit var INSTANCE: ZomboidExtensionContext
    }
}