package info.pzss.zomboid.extender.api.event

@FunctionalInterface
fun interface ZomboidScriptEventHandler<T : ZomboidScriptEvent> {
    fun handle(event: T): Boolean
}

