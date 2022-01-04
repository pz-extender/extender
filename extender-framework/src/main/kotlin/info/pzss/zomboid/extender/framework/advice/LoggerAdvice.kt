package info.pzss.zomboid.extender.framework.advice

import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import zombie.core.logger.ZLogger
import zombie.debug.DebugLogStream
import zombie.debug.DebugType
import java.util.*

/**
 * Overrides the default Project Zomboid logger with SLF4J and Logback.
 */
@Aspect
open class LoggerAdvice {
    val loggerCache = mutableMapOf<String, Logger>()
    val loggerNames = IdentityHashMap<ZLogger, String>()
    val errorLogger = KotlinLogging.logger("ErrorLogger")

    @Suppress("NOTHING_TO_INLINE")
    inline fun loggerFor(name: String) = loggerCache.computeIfAbsent(name) { LoggerFactory.getLogger(name) }

    @Suppress("NOTHING_TO_INLINE")
    inline fun doDebugLog(joinPoint: ProceedingJoinPoint, level: Level, debuggerName: String? = null) {
        val caller = DebugLogStream.tryGetCallerTraceElement(5)
        val name = debuggerName ?: if (caller == null) "(UnknownStack)" else caller.className
        val logger = loggerFor(name)

        val args = joinPoint.args.toMutableList()
        val exception = args.removeIf { it is Throwable }

        val formatArgs = when {
            args.size == 2 && args[1] is Array<*> -> args[1] as Array<*>
            args.size == 2 -> arrayOf(args[1])
            else -> emptyArray<Any>()
        }

        val format = args[0] as? String ?: "No log message provided"
        val message = if (formatArgs.isNotEmpty()) format.format(*formatArgs) else format

        when (level) {
            Level.WARN -> logger.warn(message, exception)
            Level.DEBUG -> logger.debug(message, exception)
            Level.INFO -> logger.info(message, exception)
            Level.TRACE -> logger.trace(message, exception)
            Level.ERROR -> logger.error(message, exception)
        }
    }

    @After("initialization(public zombie.core.logger.ZLogger.new(..))")
    fun captureLoggerName(joinPoint: JoinPoint) {
        loggerNames[joinPoint.`this` as ZLogger?] = joinPoint.args[0] as String
    }

    @Around("execution (public void zombie.core.logger.ExceptionLogger.logException(Throwable, String, ..)) && args(exception, message, ..)")
    fun exceptionLog(joinPoint: ProceedingJoinPoint, exception: Throwable, message: String?) {
        errorLogger.error(message ?: exception.message, exception)
    }

    @Around("execution (public void zombie.core.logger.ZLogger.write(String, String)) && args(message, scope)")
    fun scopedInfoLog(joinPoint: ProceedingJoinPoint, message: String, scope: String?) {
        val zlogger = joinPoint.`this` as ZLogger
        val logger = loggerNames[zlogger]?.let(::loggerFor)
        val logMessage = scope?.let { "$scope - $message" } ?: message

        logger?.info(logMessage)
    }

    @Around("execution (public void zombie.debug.DebugLog.log(zombie.debug.DebugType, String)) && args(debugType, message)")
    fun scopedDebugLog(joinPoint: ProceedingJoinPoint, debugType: DebugType, message: String) =
        loggerFor(debugType.name).info(message)

    @Around("execution (public void zombie.debug.DebugLogStream.warn(..))")
    fun debugLogWarn(joinPoint: ProceedingJoinPoint) = doDebugLog(joinPoint, Level.WARN)

    @Around("execution (public void zombie.debug.DebugLogStream.error(..))")
    fun debugLogError(joinPoint: ProceedingJoinPoint) = doDebugLog(joinPoint, Level.ERROR)

    @Around("execution (public void zombie.debug.DebugLogStream.println(..))")
    fun debugLogInfo(joinPoint: ProceedingJoinPoint) = doDebugLog(joinPoint, Level.INFO)
}