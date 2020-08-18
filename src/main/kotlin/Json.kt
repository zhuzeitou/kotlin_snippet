import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val jsonObject = Json {
    serializersModule = SerializersModule {
        contextual(Date::class, DateSerializer2)
        contextual(Timestamp::class, TimeStampSerializer)
    }
    ignoreUnknownKeys = true
}

inline fun <reified T> T.toJson(): String = jsonObject.encodeToString(this)

inline fun <reified T> String.parseJson(): T = jsonObject.decodeFromString(this)

typealias Date = @Contextual java.util.Date
typealias LongDate = @Serializable(with = DateSerializer::class) java.util.Date

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder) = Date(decoder.decodeLong())

    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeLong(value.time)
}

@Serializer(forClass = java.util.Date::class)
object DateSerializer2 : KSerializer<java.util.Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    private val df: ThreadLocal<DateFormat> = object : ThreadLocal<DateFormat>() {
        override fun initialValue(): DateFormat {
            return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        }
    }

    override fun deserialize(decoder: Decoder) = java.util.Date(
        LocalDateTime.parse(decoder.decodeString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    )

    override fun serialize(encoder: Encoder, value: java.util.Date) = encoder.encodeString(
        DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(
            OffsetDateTime.ofInstant(value.toInstant(), ZoneOffset.UTC)))
}

typealias Timestamp = @Serializable(with = TimeStampSerializer::class) java.sql.Timestamp

@Serializer(forClass = Timestamp::class)
object TimeStampSerializer : KSerializer<Timestamp> {
    override val descriptor = PrimitiveSerialDescriptor("Timestamp", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder) = Timestamp(decoder.decodeLong())

    override fun serialize(encoder: Encoder, value: Timestamp) = encoder.encodeLong(value.time)
}