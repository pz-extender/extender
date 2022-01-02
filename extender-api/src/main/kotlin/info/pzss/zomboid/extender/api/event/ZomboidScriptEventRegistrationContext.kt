package info.pzss.zomboid.extender.api.event

interface ZomboidScriptEventRegistrationContext {
    fun <T : ZomboidScriptEvent> register(type: Class<T>, handler: ZomboidScriptEventHandler<T>)
}

inline fun <reified T : ZomboidScriptEvent> ZomboidScriptEventRegistrationContext.register(handler: ZomboidScriptEventHandler<T>) =
    register(T::class.java, handler)

inline fun <reified T : ZomboidScriptEvent> ZomboidScriptEventRegistrationContext.event(crossinline handler: T.() -> Unit) =
    register<T> {
        it.handler()
        true
    }