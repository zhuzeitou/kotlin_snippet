import java.io.Closeable
import java.io.IOException

class Closeables(private val closeables: List<Closeable?>) : Closeable, List<Closeable?> by closeables {
    override fun close() {
        closeables.fold(null as IOException?, { e, closable ->
            try {
                closable?.close()
                e
            } catch (ex: Exception) {
                (e ?: IOException()).apply { addSuppressed(ex) }
            }
        })?.apply { throw this }
    }
}

fun closeablesOf(vararg closeables: Closeable?) = Closeables(closeables.toList())
