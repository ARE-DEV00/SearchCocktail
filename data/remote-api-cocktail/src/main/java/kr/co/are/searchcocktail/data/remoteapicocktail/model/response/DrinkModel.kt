package kr.co.are.searchcocktail.data.remoteapicocktail.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
    data class DrinkModel(
        @Json(name = "idDrink")
        val idDrink: String,
        @Json(name = "strDrink")
        val strDrink: String?,
        @Json(name = "strDrinkThumb")
        val strDrinkThumb: String?,
        @Json(name = "strDrinkAlternate")
        val strDrinkAlternate: String?,
        @Json(name = "strTags")
        val strTags: String?,
        @Json(name = "strVideo")
        val strVideo: String?,
        @Json(name = "strCategory")
        val strCategory: String?,
        @Json(name = "strIBA")
        val strIBA: String?,
        @Json(name = "strAlcoholic")
        val strAlcoholic: String?,
        @Json(name = "strGlass")
        val strGlass: String?,
        @Json(name = "strInstructions")
        val strInstructions: String?,
        @Json(name = "strInstructionsES")
        val strInstructionsEs: String?,
        @Json(name = "strInstructionsDE")
        val strInstructionsDe: String?,
        @Json(name = "strInstructionsFR")
        val strInstructionsFr: String?,
        @Json(name = "strInstructionsIT")
        val strInstructionsIt: String?,
        @Json(name = "strInstructionsZH-HANS")
        val strInstructionsZhHans: String?,
        @Json(name = "strInstructionsZH-HANT")
        val strInstructionsZhHant: String?,
        @Json(name = "strIngredient1")
        val strIngredient1: String?,
        @Json(name = "strIngredient2")
        val strIngredient2: String?,
        @Json(name = "strIngredient3")
        val strIngredient3: String?,
        @Json(name = "strIngredient4")
        val strIngredient4: String?,
        @Json(name = "strIngredient5")
        val strIngredient5: String?,
        @Json(name = "strIngredient6")
        val strIngredient6: String?,
        @Json(name = "strIngredient7")
        val strIngredient7: String?,
        @Json(name = "strIngredient8")
        val strIngredient8: String?,
        @Json(name = "strIngredient9")
        val strIngredient9: String?,
        @Json(name = "strIngredient10")
        val strIngredient10: String?,
        @Json(name = "strIngredient11")
        val strIngredient11: String?,
        @Json(name = "strIngredient12")
        val strIngredient12: String?,
        @Json(name = "strIngredient13")
        val strIngredient13: String?,
        @Json(name = "strIngredient14")
        val strIngredient14: String?,
        @Json(name = "strIngredient15")
        val strIngredient15: String?,
        @Json(name = "strMeasure1")
        val strMeasure1: String?,
        @Json(name = "strMeasure2")
        val strMeasure2: String?,
        @Json(name = "strMeasure3")
        val strMeasure3: String?,
        @Json(name = "strMeasure4")
        val strMeasure4: String?,
        @Json(name = "strMeasure5")
        val strMeasure5: String?,
        @Json(name = "strMeasure6")
        val strMeasure6: String?,
        @Json(name = "strMeasure7")
        val strMeasure7: String?,
        @Json(name = "strMeasure8")
        val strMeasure8: String?,
        @Json(name = "strMeasure9")
        val strMeasure9: String?,
        @Json(name = "strMeasure10")
        val strMeasure10: String?,
        @Json(name = "strMeasure11")
        val strMeasure11: String?,
        @Json(name = "strMeasure12")
        val strMeasure12: String?,
        @Json(name = "strMeasure13")
        val strMeasure13: String?,
        @Json(name = "strMeasure14")
        val strMeasure14: String?,
        @Json(name = "strMeasure15")
        val strMeasure15: String?,
        @Json(name = "strImageSource")
        val strImageSource: String?,
        @Json(name = "strImageAttribution")
        val strImageAttribution: String?,
        @Json(name = "strCreativeCommonsConfirmed")
        val strCreativeCommonsConfirmed: String?,
        @Json(name = "dateModified")
        val dateModified: String?

    )

/*
"idDrink": "14610",
      "strDrink": "ACID",
      "strDrinkThumb": "https://www.thecocktaildb.com/images/media/drink/xuxpxt1479209317.jpg",
      "strDrinkAlternate": null,
      "strTags": null,
      "strVideo": null,
      "strCategory": "Shot",
      "strIBA": null,
      "strAlcoholic": "Alcoholic",
      "strGlass": "Shot glass",
      "strInstructions": "Poor in the 151 first followed by the 101 served with a Coke or Dr Pepper chaser.",
      "strInstructionsES": "Vierta primero el Bacardi 151, seguido del Wild Turkey 101 y sirva con Coca-Cola o Dr Pepper.",
      "strInstructionsDE": "Gering den 151 gefolgt von der 101, die mit einer Cola oder Dr. Pepper Chaser serviert wird.",
      "strInstructionsFR": null,
      "strInstructionsIT": "Versa prima il Bacardi 151 seguito dal 101, servito con una Coca-Cola.",
      "strInstructionsZH-HANS": null,
      "strInstructionsZH-HANT": null,

      "strIngredient1": "151 proof rum",
      "strIngredient2": "Wild Turkey",
      "strIngredient3": null,
      "strIngredient4": null,
      "strIngredient5": null,
      "strIngredient6": null,
      "strIngredient7": null,
      "strIngredient8": null,
      "strIngredient9": null,
      "strIngredient10": null,
      "strIngredient11": null,
      "strIngredient12": null,
      "strIngredient13": null,
      "strIngredient14": null,
      "strIngredient15": null,
      "strMeasure1": "1 oz Bacardi ",
      "strMeasure2": "1 oz ",
      "strMeasure3": null,
      "strMeasure4": null,
      "strMeasure5": null,
      "strMeasure6": null,
      "strMeasure7": null,
      "strMeasure8": null,
      "strMeasure9": null,
      "strMeasure10": null,
      "strMeasure11": null,
      "strMeasure12": null,
      "strMeasure13": null,
      "strMeasure14": null,
      "strMeasure15": null,
      "strImageSource": null,
      "strImageAttribution": null,
      "strCreativeCommonsConfirmed": "No",
      "dateModified": "2016-11-15 11:28:37"
 */
