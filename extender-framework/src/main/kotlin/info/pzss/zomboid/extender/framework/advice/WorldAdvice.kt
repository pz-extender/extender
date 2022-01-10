package info.pzss.zomboid.extender.framework.advice

import info.pzss.zomboid.extender.api.event.world.OnCreateRandomizedBuildings
import info.pzss.zomboid.extender.api.event.world.OnCreateRandomizedVehicleStories
import info.pzss.zomboid.extender.api.event.world.OnCreateRandomizedZones
import info.pzss.zomboid.extender.framework.ZomboidExtensionContext
import org.aspectj.lang.annotation.Before
import zombie.iso.IsoWorld

class WorldAdvice {
    private val events
        get() = ZomboidExtensionContext.INSTANCE.eventDispatcher

    @Before("execution (public void zombie.iso.IsoWorld.init()) && this(world)")
    fun onWorldInit(world: IsoWorld) {
        events.dispatch(OnCreateRandomizedBuildings(world.randomizedBuildingList))
        events.dispatch(OnCreateRandomizedVehicleStories(world.randomizedVehicleStoryList))
        events.dispatch(OnCreateRandomizedZones(world.randomizedZoneList))
    }
}