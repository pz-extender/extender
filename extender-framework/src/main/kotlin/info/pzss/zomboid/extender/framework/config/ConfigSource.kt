package info.pzss.zomboid.extender.framework.config

interface ConfigSource {
    fun getOptionValueAsString(name: String): String?

    operator fun get(name: String): String? = getOptionValueAsString(name)
}