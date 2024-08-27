package kr.co.are.searchcocktail.domain.usecase

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kr.co.are.searchcocktail.domain.entity.streamtext.LexicalEntity
import kr.co.are.searchcocktail.domain.entity.streamtext.StreamTextInfoEntity
import kr.co.are.searchcocktail.domain.model.ParagraphChildAdapter
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.repository.ApiStreamTextRepository
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Base64
import java.util.zip.InflaterInputStream
import javax.inject.Inject

class GetStreamTextByIdUseCase @Inject constructor(
    private val apiStreamTextRepository: ApiStreamTextRepository
) {

    suspend operator fun invoke(
    ): Flow<ResultDomain<StreamTextInfoEntity?>> {
        return channelFlow {
            apiStreamTextRepository.getStreamText()
                .catch { exception ->
                    send(ResultDomain.Error(exception, false))
                }
                .collectLatest { resultData ->
                    when (resultData) {
                        is ResultData.Success -> {
                            /***
                             * streamText(test_data_base64.txt)
                             * 1. base64 디코딩
                             * 2. zlib 압축 해제
                             * 3. Json 파싱
                             * ***/
                            if(resultData.data?.streamText != null){
                                val json = decodeStreamText(resultData.data.streamText)
                                val moshi = Moshi.Builder()
                                    .add(ParagraphChildAdapter())
                                    .add(KotlinJsonAdapterFactory())
                                    .build()

                                val jsonAdapter = moshi.adapter(LexicalEntity::class.java)

                                val lexicalJsonObj = jsonAdapter.fromJson(json)

                                println(lexicalJsonObj)
                                resultData.data.lexicalEntity = lexicalJsonObj
                            }
                            send(ResultDomain.Success(resultData.data))
                        }

                        is ResultData.Error -> {
                            send(ResultDomain.Error(resultData.exception, resultData.isNetwork))
                        }

                        is ResultData.Loading -> {
                            send(ResultDomain.Loading)
                        }
                    }

                }
        }.flowOn(Dispatchers.IO)
    }

    private fun decodeStreamText(encodedString: String):String {
        val decompressedBytes = decompressZlib(Base64.getDecoder().decode(encodedString)) // Base64 Decode & zlib 압축 해제
        if(decompressedBytes!= null){
            val resultString = String(decompressedBytes, Charsets.UTF_8) // ByteArray를 String으로 변환
            println(resultString)
            return resultString
        }else{
            return ""
        }
    }

    private fun decompressZlib(compressedData: ByteArray): ByteArray? {
        var inflaterInputStream: InflaterInputStream? = null
        var outputStream: ByteArrayOutputStream? = null
        try {

            val inputStream = ByteArrayInputStream(compressedData)

            inflaterInputStream = InflaterInputStream(inputStream)
            outputStream = ByteArrayOutputStream()

            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (inflaterInputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            return outputStream.toByteArray() // 압축 해제된 데이터를 반환
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                inflaterInputStream?.close()
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}