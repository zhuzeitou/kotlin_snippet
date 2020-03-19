import java.io.Closeable
import java.io.IOException

class Closeables(private val closeables: List<Closeable?>) : Closeable {

    override fun close() {
        closeables.fold(null as IOException?, { e, closable ->
            tryCatch {
                closable?.close()
            } ?.let {
                (e ?: IOException()).apply {
                    addSuppressed(it)
                }
            } ?: e
        })?.apply { throw this }
    }

    private inline fun tryCatch(block: () -> Unit) : IOException? {
        return try {
            block()
            null
        } catch (e: IOException) {
            e
        }
    }
}

fun closeablesOf(vararg closeables: Closeable) = Closeables(closeables.toList())
