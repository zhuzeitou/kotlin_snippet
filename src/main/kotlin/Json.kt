import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.modules.SerializersModule
import java.sql.Timestamp

val context = SerializersModule {
    contextual(Date::class, DateSerializer)
}

val jsonObject = Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true), context)

@OptIn(ImplicitReflectionSerializer::class, UnstableDefault::class)
inline fun <reified T> T.toJson(): String = jsonObject.stringify(serializer(), this)

@OptIn(ImplicitReflectionSerializer::class, UnstableDefault::class)
inline fun <reified T> String.parseJson(): T = jsonObject.parse(serializer(), this)

typealias Date = @ContextualSerialization java.util.Date

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveDescriptor("Date", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder) = Date(decoder.decodeLong())

    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeLong(value.time)
}

typealias Timestamp = @Serializable(with = TimeStampSerializer::class) java.sql.Timestamp

@Serializer(forClass = Timestamp::class)
object TimeStampSerializer : KSerializer<Timestamp> {
    override val descriptor = PrimitiveDescriptor("Timestamp", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder) = Timestamp(decoder.decodeLong())

    override fun serialize(encoder: Encoder, value: Timestamp) = encoder.encodeLong(value.time)
}