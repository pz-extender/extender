package info.pzss.zomboid.extender.framework

import ch.qos.logback.classic.LoggerContext
import info.pzss.zomboid.extender.framework.config.ConfigSource
import info.pzss.zomboid.extender.framework.script.event.ZomboidScriptEventDispatcher
import org.slf4j.LoggerFactory


/**
 * Context holder for global extension state.
 *
 * TODO: we're unable to pass dependencies into advice, so this is the best we can do. Might be
 *       an alternative approach to organization.
 */
class ZomboidExtensionContext(
    val eventDispatcher: ZomboidScriptEventDispatcher,
    val configSources: List<ConfigSource>
) {
    fun release() {
        (LoggerFactory.getILoggerFactory() as? LoggerContext)?.stop()
    }

    companion object {
        lateinit var INSTANCE: ZomboidExtensionContext
    }
}