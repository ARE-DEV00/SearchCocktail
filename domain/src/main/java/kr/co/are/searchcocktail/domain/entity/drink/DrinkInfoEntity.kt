package kr.co.are.searchcocktail.domain.entity.drink

data class DrinkInfoEntity(
    val id: String,                         //ID
    val name: String?,                       //이름
    val thumbUrl: String? = null,           //썸네일
    val category: String? = null,           //카테고리
    val alcoholic: String? = null,          //알코올 여부
    val glass: String? = null,              //잔

    var isFavorite: Boolean = false,        //즐겨찾기 여부

    //소개
    val instructions: String? = null,       //영어(English)
    val instructionsEs: String? = null,      //스페인어(Spanish)
    val instructionsDe: String? = null,      //독일어(German)
    val instructionsFr: String? = null,      //프랑스어(French)
    val instructionsIt: String? = null,      //이탈리아어(Italian)
    val instructionsZhHans: String? = null,  //중국어 간체(Chinese)
    val instructionsZhHant: String? = null,  //중국어 번체(Chinese)

    //재료
    val ingredient1: String? = null,
    val ingredient2: String? = null,
    val ingredient3: String? = null,
    val ingredient4: String? = null,
    val ingredient5: String? = null,
    val ingredient6: String? = null,
    val ingredient7: String? = null,
    val ingredient8: String? = null,
    val ingredient9: String? = null,
    val ingredient10: String? = null,
    val ingredient11: String? = null,
    val ingredient12: String? = null,
    val ingredient13: String? = null,
    val ingredient14: String? = null,
    val ingredient15: String? = null,

    //재료량
    val measure1: String? = null,
    val measure2: String? = null,
    val measure3: String? = null,
    val measure4: String? = null,
    val measure5: String? = null,
    val measure6: String? = null,
    val measure7: String? = null,
    val measure8: String? = null,
    val measure9: String? = null,
    val measure10: String? = null,
    val measure11: String? = null,
    val measure12: String? = null,
    val measure13: String? = null,
    val measure14: String? = null,
    val measure15: String? = null,

    val dateModified: String? = null,
)