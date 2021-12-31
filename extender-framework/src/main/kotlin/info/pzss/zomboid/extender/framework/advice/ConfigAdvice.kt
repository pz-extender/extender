package info.pzss.zomboid.extender.framework.advice

import com.google.common.base.CaseFormat
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import zombie.config.ConfigOption


private fun String.toEnvironmentVariableName() = "ZOMBOID_${replace('.', '_')}"

private val logger = KotlinLogging.logger {}

/**
 * Instruments zomboids [ConfigOption] classes with code that will first attempt to resolve the option value
 * from an environment variable.
 */
@Aspect
open class ConfigAdvice {
    @After("initialization (public zombie.config.*ConfigOption.new(..)) && !initialization (public zombie.config.ConfigOption.new(..))")
    open fun serverOptionsFromEnvVars(joinPoint: JoinPoint) {
        val option = joinPoint.`this` as ConfigOption
        val optionName = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, option.name.toEnvironmentVariableName())
        val env = System.getenv()

        env[optionName]?.also {
            option.parse(it)
        }
    }
}