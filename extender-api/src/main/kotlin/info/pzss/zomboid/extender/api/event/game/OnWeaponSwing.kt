package info.pzss.zomboid.extender.api.event.game

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import info.pzss.zomboid.extender.api.event.annotation.EventName
import zombie.characters.IsoGameCharacter
import zombie.inventory.types.HandWeapon

@EventName("OnWeaponSwing")
class OnWeaponSwing(val character: IsoGameCharacter, val weapon: HandWeapon) : ZomboidScriptEvent