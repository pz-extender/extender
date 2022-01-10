package info.pzss.zomboid.extender.api.event

typealias EventCtx = ZomboidScriptEventRegistrationContext
typealias EventHandler<T> = T.() -> Unit

inline fun <reified T : ZomboidScriptEvent> EventCtx.eventHandlerFactory(): (EventHandler<T>) -> Unit = ::event
