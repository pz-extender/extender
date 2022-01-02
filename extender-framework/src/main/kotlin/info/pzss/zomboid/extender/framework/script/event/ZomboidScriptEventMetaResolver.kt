package info.pzss.zomboid.extender.framework.script.event

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import info.pzss.zomboid.extender.api.event.annotation.EventName

class ZomboidScriptEventMetaResolver {
    fun nameForClass(ty: Class<out ZomboidScriptEvent>) : String =
        ty.annotations.firstNotNullOfOrNull { it as? EventName }?.name ?: ty.simpleName
}