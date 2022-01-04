package info.pzss.zomboid.extender.api.event.game

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import info.pzss.zomboid.extender.api.event.annotation.EventName
import zombie.inventory.ItemContainer

@EventName("OnFillContainer")
class OnFillContainer(val roomName: String, val containerType: String, val container: ItemContainer) :
    ZomboidScriptEvent