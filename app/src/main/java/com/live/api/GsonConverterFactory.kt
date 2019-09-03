package com.live.api

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import com.live.utils.logE
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.nio.charset.Charset

/**
 * retrofit Converter 工厂
 */
class GsonConverterFactory private constructor(private val gson: Gson) : Converter.Factory() {

    companion object {
        @JvmOverloads
        fun create(gson: Gson? = Gson()): GsonConverterFactory {
            if (gson == null) throw NullPointerException("gson == null")
            return GsonConverterFactory(gson)
        }
    }

    override fun responseBodyConverter(
        type: Type?, annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type!!))
        return GsonResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation>?,
        methodAnnotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type!!))
        return GsonRequestBodyConverter(gson, adapter)
    }
}

/**
 * 请求参数RequestBody gson 解析
 */
class GsonRequestBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
    Converter<T, RequestBody> {

    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        value.toString().logE()
        if (value is CharSequence || value is Number || value is Boolean) {
            return value.toString().toRequestBody(MEDIA_TYPE)
        }

        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return buffer.readByteString().toRequestBody(MEDIA_TYPE)
    }

    companion object {
        private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaType()
        private val UTF_8 = Charset.forName("UTF-8")
    }
}

/**
 * 返回数据ResponseBody gson 解析
 */
class GsonResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val jsonReader = gson.newJsonReader(value.charStream())
        value.use {
            val result = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            return result
        }
    }
}
