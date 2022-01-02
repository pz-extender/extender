package info.pzss.zomboid.extender.framework.script.event

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import info.pzss.zomboid.extender.api.event.ZomboidScriptEventHandler
import info.pzss.zomboid.extender.api.event.ZomboidScriptEventRegistrationContext

class ZomboidScriptEventDispatcher(val metaResolver: ZomboidScriptEventMetaResolver) :
    ZomboidScriptEventRegistrationContext {

    private val handlerChainsByName = mutableMapOf<String, EventHandlerChain>()

    override fun <T : ZomboidScriptEvent> register(type: Class<T>, handler: ZomboidScriptEventHandler<T>) {
        val eventName = metaResolver.nameForClass(type)
        val handlerList = handlerChainsByName.computeIfAbsent(eventName) {
            val factory = ZomboidScriptEventFactory.fromOnlyConstructor(type)
            val handlers = mutableListOf<ZomboidScriptEventHandler<*>>()

            EventHandlerChain(factory, handlers)
        }

        handlerList.handlers.add(handler)
    }

    fun dispatch(eventName: String, arguments: Array<Any>): Boolean {
        val chain = handlerChainsByName[eventName] ?: return true
        val factory = chain.factory as ZomboidScriptEventFactory<ZomboidScriptEvent>
        val handlers = chain.handlers as List<ZomboidScriptEventHandler<ZomboidScriptEvent>>
        val event = factory.create(eventName, arguments)

        for (handler in handlers) {
            if (!handler.handle(event)) {
                return false
            }
        }

        return true
    }

    internal class EventHandlerChain(
        val factory: ZomboidScriptEventFactory<*>,
        val handlers: MutableList<ZomboidScriptEventHandler<*>>
    )
}