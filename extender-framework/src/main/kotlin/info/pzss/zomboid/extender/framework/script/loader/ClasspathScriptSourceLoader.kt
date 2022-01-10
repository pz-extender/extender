package info.pzss.zomboid.extender.framework.script.loader

import info.pzss.zomboid.extender.api.ZomboidScript
import info.pzss.zomboid.extender.framework.script.ZomboidScriptSource
import info.pzss.zomboid.extender.framework.script.ZomboidScriptSourceLoader
import io.github.classgraph.ClassGraph
import kotlinx.coroutines.flow.flow

class ClasspathScriptSourceLoader : ZomboidScriptSourceLoader {
    override suspend fun load() = flow {
        val classGraph = ClassGraph()

        val scan = classGraph
            .enableClassInfo()
            .acceptClasspathElementsContainingResourcePath("media/pz-script/*")
            .scan()

        scan.use { scanResult ->
            scanResult.getResourcesWithExtension("kts")
                .filter { it.path.endsWith("pz.kts") }
                .forEach { emit(ZomboidScriptSource.SourceResource(it)) }

            scanResult.getSubclasses(ZomboidScript::class.java)
                .loadClasses()
                .forEach {
                    emit(ZomboidScriptSource.CompiledResource(it as Class<out ZomboidScript>))
                }
        }
    }
}