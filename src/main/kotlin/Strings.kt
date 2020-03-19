import java.security.MessageDigest

private val HEX_CHARS = "0123456789ABCDEF"

fun String.md5(): ByteArray {
    return MessageDigest.getInstance("MD5").digest(this.toByteArray())
}

fun ByteArray.toHex(): String {
    return fold(StringBuilder(size * 2), { builder, b ->
        with(b.toInt()) {
            val hi = (this shr 4) and 0x0F
            val lo = this and 0x0F
            builder.append(HEX_CHARS[hi]).append(HEX_CHARS[lo])
        }
    }).toString()
}




