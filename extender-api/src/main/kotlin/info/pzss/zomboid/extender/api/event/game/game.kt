package info.pzss.zomboid.extender.api.event.game

import info.pzss.zomboid.extender.api.event.EventCtx
import info.pzss.zomboid.extender.api.event.eventHandlerFactory

val EventCtx.onGameStart get() = eventHandlerFactory<OnGameStart>()
val EventCtx.onFillContainer get() = eventHandlerFactory<OnFillContainer>()
val EventCtx.onWeaponSwing get() = eventHandlerFactory<OnWeaponSwing>()
