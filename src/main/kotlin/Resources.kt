import java.nio.charset.Charset

private val classLoader by lazy { object {}.javaClass.classLoader }

fun String.asResource() = classLoader.getResource(this)

fun String.asResourceBytes() = asResource()?.readBytes()

fun String.asResourceText(charset: Charset = Charsets.UTF_8) = asResource()?.readText(charset)

fun String.asResourceStream() = classLoader.getResourceAsStream(this)