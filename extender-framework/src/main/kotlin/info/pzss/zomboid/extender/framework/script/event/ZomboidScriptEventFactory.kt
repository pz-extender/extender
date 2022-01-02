package info.pzss.zomboid.extender.framework.script.event

import info.pzss.zomboid.extender.api.event.ZomboidScriptEvent
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType


class ZomboidScriptEventFactory<T : ZomboidScriptEvent>(private val handle: MethodHandle) {
    fun create(name: String, args: Array<Any>): T {
        return handle.invokeExact(args) as? T ?: error("couldn't create correct script event object")
    }

    companion object {
        fun <T : ZomboidScriptEvent> fromOnlyConstructor(type: Class<T>): ZomboidScriptEventFactory<out T> {
            val lookup = MethodHandles.lookup()
            val constructors = type.declaredConstructors
            if (constructors.size > 1) {
                error("$type should have a single constructor")
            }

            val constructorArgs = constructors[0].parameterTypes
            val constructor = lookup.findConstructor(type, MethodType.methodType(Void.TYPE, constructorArgs))
            val qualifiedConstructorType = constructor.type().changeReturnType(ZomboidScriptEvent::class.java)
            val qualifiedConstructor = constructor
                .asType(qualifiedConstructorType)
                .asSpreader(Array<Any>::class.java, constructorArgs.size)

            return ZomboidScriptEventFactory(qualifiedConstructor)
        }
    }
}