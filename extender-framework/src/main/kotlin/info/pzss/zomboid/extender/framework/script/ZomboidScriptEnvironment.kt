package info.pzss.zomboid.extender.framework.script

import info.pzss.zomboid.extender.api.ZomboidScript
import info.pzss.zomboid.extender.api.event.ZomboidScriptEventRegistrationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.merge
import mu.KotlinLogging
import java.nio.file.FileSystems
import java.nio.file.WatchService
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

private val logger = KotlinLogging.logger {}

class ZomboidScriptEnvironment(
    eventContext: ZomboidScriptEventRegistrationContext,
    reloadChangedScripts: Boolean = false
) {
    /* TODO */
    // private val reloadCoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
    // private val reloadWatchService: WatchService?
    private val host = BasicJvmScriptingHost()
    private val compileConfiguration = createJvmCompilationConfigurationFromTemplate<ZomboidScript>()
    private val evaluationConfiguration = createJvmEvaluationConfigurationFromTemplate<ZomboidScript> {
        implicitReceivers(eventContext)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun load(vararg loaders: ZomboidScriptSourceLoader) {
        runBlocking {
            val allScriptSourceRx = loaders.map { it.load().flowOn(Dispatchers.IO) }.toTypedArray()
            val scriptSourceRx = merge(*allScriptSourceRx)

            scriptSourceRx.collect { source ->
                logger.info { "Loading ZomboidScript named ${source.name()}" }
                source.evaluate(host, compileConfiguration, evaluationConfiguration)
            }
        }
    }
}