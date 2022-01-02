package info.pzss.zomboid.extender.framework

import info.pzss.zomboid.extender.framework.script.ZomboidScriptEnvironment
import info.pzss.zomboid.extender.framework.script.event.ZomboidScriptEventDispatcher
import info.pzss.zomboid.extender.framework.script.event.ZomboidScriptEventMetaResolver
import info.pzss.zomboid.extender.framework.script.loader.ClasspathScriptSourceLoader


class ZomboidExtensionBootstrap {
    fun bootstrap(): ZomboidExtensionContext {
        val eventMetaResolver = ZomboidScriptEventMetaResolver()
        val eventDispatcher = ZomboidScriptEventDispatcher(eventMetaResolver)

        val scriptEnvironment = ZomboidScriptEnvironment(eventDispatcher, /*true*/)
        scriptEnvironment.load(ClasspathScriptSourceLoader())

        return ZomboidExtensionContext(eventDispatcher)
    }
}