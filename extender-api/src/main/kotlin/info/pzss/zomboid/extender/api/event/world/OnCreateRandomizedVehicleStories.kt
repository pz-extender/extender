package info.pzss.zomboid.extender.api.event.world

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import zombie.randomizedWorld.randomizedVehicleStory.RandomizedVehicleStoryBase

class OnCreateRandomizedVehicleStories(val entries: MutableList<RandomizedVehicleStoryBase>) : ZomboidScriptEvent