package info.pzss.zomboid.extender.framework.advice

import info.pzss.zomboid.extender.framework.ZomboidExtensionInitializer
import info.pzss.zomboid.extender.framework.ZomboidExtensionContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
open class LifecycleAdvice {
    @Suppress("NOTHING_TO_INLINE")
    inline fun startupAndShutdown(joinPoint: ProceedingJoinPoint) {
        ZomboidExtensionContext.INSTANCE = ZomboidExtensionInitializer().init()

        try {
            joinPoint.proceed()
        } finally {
            ZomboidExtensionContext.INSTANCE.release()
        }
    }

    @Around("execution (public static void zombie.network.GameServer.main(String[]))")
    open fun serverStartupAndShutdown(joinPoint: ProceedingJoinPoint) =
        startupAndShutdown(joinPoint)

    @Around(value = "execution (public static void zombie.gameStates.MainScreenState.main(String[]))")
    open fun clientStartupAndShutdown(joinPoint: ProceedingJoinPoint) =
        startupAndShutdown(joinPoint)
}