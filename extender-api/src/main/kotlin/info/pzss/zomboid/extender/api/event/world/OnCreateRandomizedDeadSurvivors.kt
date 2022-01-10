package info.pzss.zomboid.extender.api.event.world

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import zombie.randomizedWorld.randomizedDeadSurvivor.RandomizedDeadSurvivorBase

class OnCreateRandomizedDeadSurvivors(val entries: MutableList<RandomizedDeadSurvivorBase>) : ZomboidScriptEvent