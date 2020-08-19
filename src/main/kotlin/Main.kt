
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import okhttp3.FormBody
import okhttp3.OkHttpClient
import java.lang.Exception
import java.net.URLEncoder
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

@Serializable
data class Form(val a: Int, val b: Int)

@Serializable
data class HttpBin<T>(val form: T)

@OptIn(ExperimentalTime::class)
inline fun <reified T> parseResource(name: String): T? {
    return try {
        name.asResourceText()?.let { str ->
            val mark = TimeSource.Monotonic.markNow()
            val result = str.parseJson<T>()
            println("parseResource ${name} ${mark.elapsedNow()}")
            result
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@OptIn(ExperimentalTime::class)
fun main() {
    val client = OkHttpClient()
    val request = okhttp3.Request.Builder()
        .url("http://httpbin.org/post")
        .post(FormBody.Builder()
            .add("a", "1")
            .add("b", "12345")
            .build())
        .header("isp", URLEncoder.encode("你好", "utf-8"))
        .build()
    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            response.body?.let { body ->
                println(body.string())
            }
        }
    }

    val mark = TimeSource.Monotonic.markNow()
    test()
    println("test ${mark.elapsedNow()}")

    val users = parseResource<List<UserProfile>>("user.json")
    val citys = parseResource<List<CityInfo>>("citys.json")
    val repos = parseResource<List<Repo>>("repos.json")
    val requests = parseResource<List<Request>>("request.json")
    println(citys)
}

@Serializable
data class HelloJson<T>(val code: Int, val message: String, val data: T? = null)

@Serializable
data class HelloJson2(val a: Int, val b: Timestamp)

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