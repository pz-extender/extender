package info.pzss.zomboid.extender.api.event.world

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import zombie.randomizedWorld.randomizedBuilding.RandomizedBuildingBase

class OnCreateRandomizedBuildings(val entries: MutableList<RandomizedBuildingBase>) : ZomboidScriptEvent