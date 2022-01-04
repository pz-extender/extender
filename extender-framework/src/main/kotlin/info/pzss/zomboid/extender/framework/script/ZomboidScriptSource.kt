package info.pzss.zomboid.extender.framework.script

import info.pzss.zomboid.extender.api.ZomboidScript
import io.github.classgraph.Resource
import kotlinx.coroutines.flow.Flow
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchKey
import java.nio.file.WatchService
import kotlin.io.path.toPath
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.host.BasicScriptingHost
import kotlin.script.experimental.host.FileScriptSource
import kotlin.script.experimental.host.UrlScriptSource


interface ZomboidScriptSourceLoader {
    suspend fun load(): Flow<ZomboidScriptSource>
}

sealed interface ZomboidScriptSourceWatcher {
    class Key(val watchKey: WatchKey) : ZomboidScriptSourceWatcher
    object Unsupported : ZomboidScriptSourceWatcher
}

sealed interface ZomboidScriptSource {
    class CompiledResource(private val ty: Class<out ZomboidScript>) : ZomboidScriptSource {
        override fun evaluate(
            host: BasicScriptingHost,
            compilation: ScriptCompilationConfiguration,
            evaluationConfiguration: ScriptEvaluationConfiguration
        ) {
            val receivers = evaluationConfiguration[ScriptEvaluationConfiguration.implicitReceivers] ?: emptyList()
            val receiverTypes = receivers.map { it.javaClass }.toTypedArray()

            val scriptConstructor = ty.getConstructor(*receiverTypes)
            scriptConstructor.newInstance(*receivers.toTypedArray())
        }

        override fun name() = ty.simpleName

        override fun watchChanges(watcher: WatchService) = ZomboidScriptSourceWatcher.Unsupported
    }

    class SourceResource(private val resource: Resource) : ZomboidScriptSource {
        override fun evaluate(
            host: BasicScriptingHost,
            compilation: ScriptCompilationConfiguration,
            evaluationConfiguration: ScriptEvaluationConfiguration
        ) {
            host.eval(UrlScriptSource(resource.url), compilation, evaluationConfiguration)
        }

        override fun name() = resource.pathRelativeToClasspathElement.substringAfterLast("/")

        override fun watchChanges(watcher: WatchService) = when (resource.uri.scheme) {
            "file" -> {
                val resourcePath = resource.uri.toPath()
                val key = resourcePath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE)

                ZomboidScriptSourceWatcher.Key(key)
            }
            else -> ZomboidScriptSourceWatcher.Unsupported
        }
    }

    class SourceFile(private val path: Path) : ZomboidScriptSource {
        override fun evaluate(
            host: BasicScriptingHost,
            compilation: ScriptCompilationConfiguration,
            evaluationConfiguration: ScriptEvaluationConfiguration
        ) {
            host.eval(FileScriptSource(path.toFile()), compilation, evaluationConfiguration)
        }

        override fun name() = path.fileName.toString()

        override fun watchChanges(watcher: WatchService) =
            ZomboidScriptSourceWatcher.Key(path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY))
    }

    fun evaluate(
        host: BasicScriptingHost,
        compilation: ScriptCompilationConfiguration,
        evaluationConfiguration: ScriptEvaluationConfiguration
    )

    fun name(): String

    fun watchChanges(watcher: WatchService): ZomboidScriptSourceWatcher
}