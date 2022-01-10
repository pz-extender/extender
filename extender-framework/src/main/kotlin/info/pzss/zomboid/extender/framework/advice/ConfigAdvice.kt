package info.pzss.zomboid.extender.framework.advice

import info.pzss.zomboid.extender.framework.ZomboidExtensionContext
import info.pzss.zomboid.extender.framework.config.ConfigSource
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import zombie.config.ConfigOption


private val logger = KotlinLogging.logger {}

/**
 * Instruments zomboids [ConfigOption] classes with code that will first attempt to resolve the option value
 * from an environment variable.
 */
@Aspect
open class ConfigAdvice {

    private val configSources: List<ConfigSource>
        get() = ZomboidExtensionContext.INSTANCE.configSources

    @After("initialization (public zombie.config.*ConfigOption.new(..)) && !initialization (public zombie.config.ConfigOption.new(..))")
    open fun serverOptionsFromEnvVars(joinPoint: JoinPoint) {
        val option = joinPoint.`this` as ConfigOption
        val optionValue = configSources.firstNotNullOfOrNull { it.getOptionValueAsString(option.name) }

        optionValue?.also {
            option.parse(it)
        }
    }
}