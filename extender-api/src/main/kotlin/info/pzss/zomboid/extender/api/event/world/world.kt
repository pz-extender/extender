package info.pzss.zomboid.extender.api.event.world

import info.pzss.zomboid.extender.api.event.EventCtx
import info.pzss.zomboid.extender.api.event.eventHandlerFactory

val EventCtx.onCreateRandomizedBuildings get() = eventHandlerFactory<OnCreateRandomizedBuildings>()
val EventCtx.onCreateRandomizedVehicleStories get() = eventHandlerFactory<OnCreateRandomizedVehicleStories>()
val EventCtx.onCreateRandomizedZones get() = eventHandlerFactory<OnCreateRandomizedZones>()

