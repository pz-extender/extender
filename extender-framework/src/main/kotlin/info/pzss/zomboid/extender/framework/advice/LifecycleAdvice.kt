package info.pzss.zomboid.extender.framework.advice

import info.pzss.zomboid.extender.framework.ZomboidExtenderContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
open class LifecycleAdvice {
    @Suppress("NOTHING_TO_INLINE")
    inline fun startupAndShutdown(joinPoint: ProceedingJoinPoint) {
        ZomboidExtenderContext.bootstrap()

        try {
            joinPoint.proceed()
        } finally {
            ZomboidExtenderContext.shutdown()
        }
    }

    @Around("execution (public static void zombie.network.GameServer.main(String[]))")
    open fun serverStartupAndShutdown(joinPoint: ProceedingJoinPoint) =
        startupAndShutdown(joinPoint)

    @Around(value = "execution (public static void zombie.gameStates.MainScreenState.main(String[]))")
    open fun clientStartupAndShutdown(joinPoint: ProceedingJoinPoint) =
        startupAndShutdown(joinPoint)
}