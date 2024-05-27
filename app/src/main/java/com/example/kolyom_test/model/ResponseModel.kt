package com.example.kolyom_test.model

import com.google.gson.annotations.SerializedName

data class ResponseModel(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("nameAr")
	val nameAr: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("isoThreeCharacter")
	val isoThreeCharacter: String? = null,

	@field:SerializedName("currencyName")
	val currencyName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nameEn")
	val nameEn: String? = null,

	@field:SerializedName("phoneNumberMaxLength")
	val phoneNumberMaxLength: Int? = null,

	@field:SerializedName("flagUrl")
	val flagUrl: String? = null,

	@field:SerializedName("isoTwoCharacter")
	val isoTwoCharacter: String? = null,

	@field:SerializedName("currencyCode")
	val currencyCode: String? = null
)
