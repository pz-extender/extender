package info.pzss.zomboid.extender.framework.advice

import info.pzss.zomboid.extender.api.event.world.OnCreateRandomizedBuildings
import info.pzss.zomboid.extender.api.event.world.OnCreateRandomizedDeadSurvivors
import info.pzss.zomboid.extender.api.event.world.OnCreateRandomizedVehicleStories
import info.pzss.zomboid.extender.api.event.world.OnCreateRandomizedZones
import info.pzss.zomboid.extender.framework.ZomboidExtensionContext
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import zombie.iso.IsoWorld
import zombie.randomizedWorld.randomizedBuilding.RBBasic

@Aspect
class WorldAdvice {
    private val events
        get() = ZomboidExtensionContext.INSTANCE.eventDispatcher

    @After("initialization (public zombie.randomizedWorld.randomizedBuilding.RBBasic.new(..)) && this(basic)")
    fun onBasicBuildingInit(basic: RBBasic) {
        events.dispatch(OnCreateRandomizedDeadSurvivors(basic.survivorStories))
    }

    @Before("execution (public void zombie.iso.IsoWorld.init()) && this(world)")
    fun onWorldInit(world: IsoWorld) {
        events.dispatch(OnCreateRandomizedBuildings(world.randomizedBuildingList))
        events.dispatch(OnCreateRandomizedVehicleStories(world.randomizedVehicleStoryList))
        events.dispatch(OnCreateRandomizedZones(world.randomizedZoneList))
    }
}