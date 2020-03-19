import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.Parameters
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import java.net.URLEncoder
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

@Serializable
data class Form(val a: Int, val b: Int)

@Serializable
data class HttpBin<T>(val form: T)

@OptIn(ExperimentalTime::class)
fun main() {
    runBlocking {
        val client = HttpClient(Apache) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(jsonObject)
            }
        }

        closeablesOf(client).use {
            val body = client.post<HttpBin<Form>> {
                url("http://httpbin.org/post")
                body = FormDataContent(Parameters.build {
                    append("a", "1")
                    append("b", "12345")
                })
                header("isp", URLEncoder.encode("你好", "utf-8"))
            }.form
            println(body)
        }

        val mark = TimeSource.Monotonic.markNow()
        test()
        println(mark.elapsedNow())
    }
}

@Serializable
data class HelloJson<T>(val code: Int, val message: String, val data: T? = null)

@Serializable
data class HelloJson2(val a: Int, val b: Timestamp)

@OptIn(ExperimentalStdlibApi::class)
fun test() {
    val data = HelloJson(0, "success", listOf("aaa", "bbb", "ccc"))

    val jsonStr = data.toJson()
    val data2 = jsonStr.parseJson<HelloJson<List<String>>>()

    println(jsonStr)
    println(data2)

    val jsonStr2 = """{"a": 444, "b": 1583977630123, "c": 123}"""
    val data3 = jsonStr2.parseJson<HelloJson2>()
    val jsonStr3 = data3.toJson()

    println(data3)
    println(jsonStr3)

    val data4 = HelloJson2(2342, Timestamp(System.currentTimeMillis()))
    val jsonStr4 = data4.toJson()

    println(jsonStr4)
}