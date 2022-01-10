package info.pzss.zomboid.extender.framework.config

import com.google.common.base.CaseFormat

private val acronymRegex = Regex("(?<=\\p{Lower}|\\A)(\\p{Upper}+)(?=(\\p{Upper}\\p{Lower})|\\Z)")
private val pascalCaseToUnderscore = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE)
private val shoutingToPascalCase = pascalCaseToUnderscore.reverse()

internal fun String.fixAcronyms() = replace(acronymRegex) { shoutingToPascalCase.convert(it.value)!! }
internal fun String.fixSeparators() = replace(".", "_")
internal fun String.toEnvironmentVarName() = pascalCaseToUnderscore.convert(fixAcronyms().fixSeparators())

class EnvironmentConfigSource(private val variables: Map<String, String> = System.getenv()) : ConfigSource {
    override fun getOptionValueAsString(name: String) = variables[name.toEnvironmentVarName()]
}