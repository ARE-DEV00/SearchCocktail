package kr.co.are.searchcocktail.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.are.searchcocktail.domain.entity.DrinkInfoEntity
import kr.co.are.searchcocktail.domain.entity.StreamTextInfoEntity
import kr.co.are.searchcocktail.domain.model.ResultData

interface ApiStreamTextRepository {

    suspend fun getStreamText(): Flow<ResultData<StreamTextInfoEntity?>>
}