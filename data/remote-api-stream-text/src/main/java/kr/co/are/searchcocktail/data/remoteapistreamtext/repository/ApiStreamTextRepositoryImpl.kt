package kr.co.are.searchcocktail.data.remoteapistreamtext.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.are.searchcocktail.data.remoteapistreamtext.ApiStreamTextService
import kr.co.are.searchcocktail.domain.entity.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.entity.StreamTextInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData
import kr.co.are.searchcocktail.domain.repository.ApiStreamTextRepository
import java.net.UnknownHostException
import javax.inject.Inject

class ApiStreamTextRepositoryImpl @Inject constructor(
    private val apiStreamTextService: ApiStreamTextService,
) : ApiStreamTextRepository {
    override suspend fun getStreamText(): Flow<ResultData<StreamTextInfoEntity?>> {
        return flow {
            try {
                emit(ResultData.Loading)
                val response = apiStreamTextService.getFilterByAlcoholic()
                if (response.isSuccessful) {
                    val model = response.body()?.data
                    if (model != null) {
                        emit(
                            ResultData.Success(
                                StreamTextInfoEntity(
                                    id = model.id,
                                    title = model.title,
                                    videoUrl = model.videoUrl,
                                    streamText = model.streamText,
                                    createdAt = model.createdAt,
                                    updatedAt = model.updatedAt
                                )
                            )
                        )
                    } else {
                        emit(ResultData.Success(null))
                    }

                } else {
                    emit(ResultData.Error(Exception("API Error: ${response.message()}"), false))
                }
            } catch (uhe: UnknownHostException) {
                emit(ResultData.Error(uhe, true))
            } catch (t: Throwable) {
                emit(ResultData.Error(t, false))
            }
        }
    }


}
