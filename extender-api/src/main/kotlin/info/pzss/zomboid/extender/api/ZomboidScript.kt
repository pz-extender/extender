package info.pzss.zomboid.extender.api

import info.pzss.zomboid.extender.api.event.ZomboidScriptEventRegistrationContext
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.jvm

class ZomboidScriptEvaluationConfiguration : ScriptEvaluationConfiguration({
    implicitReceivers(ZomboidScriptEventRegistrationContext::class)
})

class ZomboidScriptCompilationConfiguration : ScriptCompilationConfiguration({
    implicitReceivers(ZomboidScriptEventRegistrationContext::class)
    defaultImports(
        "info.pzss.zomboid.extender.api.event.game.*",
        "info.pzss.zomboid.extender.api.event.*",
        "zombie.characters.professions.*",
        "zombie.characters.skills.*",
        "zombie.characters.traits.*",
        "zombie.characters.WornItems.*",
        "zombie.characters.Moodles.*",
        "zombie.characters.BodyDamage.*",
        "zombie.characters.*",
        "zombie.core.*",
        "zombie.*",
        "zombie.input.*",
        "zombie.interfaces.*",
        "zombie.inventory.*",
        "zombie.inventory.types.*",
        "zombie.iso.*",
        "zombie.iso.areas.*",
        "zombie.iso.areas.isoregion.*",
        "zombie.iso.areas.isoregion.data.*",
        "zombie.iso.areas.isoregion.jobs.*",
        "zombie.iso.areas.isoregion.regions.*",
        "zombie.iso.objects.*",
        "zombie.iso.objects.interfaces.*",
        "zombie.iso.sprite.*",
        "zombie.iso.sprite.shapers.*",
        "zombie.iso.SpriteDetails.*",
        "zombie.iso.weather.*",
        "zombie.iso.weather.dbg.*",
        "zombie.iso.weather.fog.*",
        "zombie.iso.weather.fx.*",
    )

    jvm {
        dependenciesFromClassContext(ZomboidScript::class, wholeClasspath = true)
    }

    ide {
        acceptedLocations(ScriptAcceptedLocation.Everywhere)
    }
})

@KotlinScript(
    fileExtension = "pz.kts",
    compilationConfiguration = ZomboidScriptCompilationConfiguration::class,
    evaluationConfiguration = ZomboidScriptEvaluationConfiguration::class
)
open class ZomboidScript