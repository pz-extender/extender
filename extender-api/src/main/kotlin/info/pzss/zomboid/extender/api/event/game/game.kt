package info.pzss.zomboid.extender.api.event.game

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import info.pzss.zomboid.extender.api.event.ZomboidScriptEventRegistrationContext
import info.pzss.zomboid.extender.api.event.event

typealias EventCtx = ZomboidScriptEventRegistrationContext
typealias EventHandler<T> = T.() -> Unit

inline fun <reified T : ZomboidScriptEvent> EventCtx.eventHandlerFactory(): (EventHandler<T>) -> Unit = ::event

val EventCtx.onGameStart get() = eventHandlerFactory<OnGameStart>()
val EventCtx.onFillContainer get() = eventHandlerFactory<OnFillContainer>()
val EventCtx.onWeaponSwing get() = eventHandlerFactory<OnWeaponSwing>()
