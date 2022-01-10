package info.pzss.zomboid.extender.api.event.world

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import zombie.randomizedWorld.randomizedZoneStory.RandomizedZoneStoryBase

class OnCreateRandomizedZones(val entries: MutableList<RandomizedZoneStoryBase>): ZomboidScriptEvent