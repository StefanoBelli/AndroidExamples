package it.ste.archcompcoroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class Repository {
    companion object {
        suspend fun fetch(uri : String) : Response {
            return withContext(Dispatchers.IO) {
                val url = URL(uri)
                var code : Int
                var msg : String
                var content : String
                val name = if (url.file == null || url.file.isBlank()) "index.html" else url.file

                try {
                    with(url.openConnection() as HttpURLConnection) {
                        inputStream.bufferedReader().use { content = it.readText() }
                        code = responseCode
                        msg = responseMessage
                    }
                } catch(ioe : IOException) {
                    code = -1
                    msg = "Could not resolve \"${url.host}\" or requested resource \"$name\" " +
                            "not found (HTTP 404)"
                    content = ""
                }

                return@withContext Response(code,msg,name,content)
            }
        }
    }
}