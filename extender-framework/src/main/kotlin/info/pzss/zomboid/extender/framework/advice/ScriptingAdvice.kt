package info.pzss.zomboid.extender.framework.advice

import info.pzss.zomboid.extender.framework.ZomboidExtensionContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
open class ScriptingAdvice {

    private val eventDispatcher
        get() = ZomboidExtensionContext.INSTANCE.eventDispatcher

    @Around("execution (public void zombie.Lua.LuaEventManager.triggerEvent(..))")
    fun interceptLuaEvent(joinPoint: ProceedingJoinPoint) {
        val args = joinPoint.args.toMutableList()
        val eventName = args.removeAt(0) as String

        // Don't invoke regular Lua scripts unless the handler chain passed on the event.
        if (eventDispatcher.dispatch(eventName, args.toTypedArray())) {
            joinPoint.proceed()
        }
    }
}