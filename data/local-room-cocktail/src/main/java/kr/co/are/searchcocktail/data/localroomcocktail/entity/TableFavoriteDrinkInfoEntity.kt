package kr.co.are.searchcocktail.data.localroomcocktail.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kr.co.are.searchcocktail.data.localroomcocktail.CocktailDataBaseKey
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Entity(tableName = CocktailDataBaseKey.TABLE_FAVORITE_DRINK_INFO)
data class TableFavoriteDrinkInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val key: Long = 0,

    val id: String,                        //아이디
    val name: String?,                       //이름
    val thumbUrl: String? = null,           //썸네일
    val category: String? = null,           //카테고리

    var createdTime: LocalDateTime = LocalDateTime.now(ZoneId.from(ZoneOffset.UTC)), //생성시간
    var modifiedTime: LocalDateTime = LocalDateTime.now(ZoneId.from(ZoneOffset.UTC))  //수정시간

)

