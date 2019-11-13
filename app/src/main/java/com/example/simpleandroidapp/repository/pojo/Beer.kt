package com.example.simpleandroidapp.repository.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Beer {
    @SerializedName("id")
    @Expose
    private val id: Int? = null
    @SerializedName("name")
    @Expose
    val name: String? = null
    @SerializedName("tagline")
    @Expose
    private val tagline: String? = null
    @SerializedName("first_brewed")
    @Expose
    private val firstBrewed: String? = null
    @SerializedName("description")
    @Expose
    private val description: String? = null
    @SerializedName("image_url")
    @Expose
    private val imageUrl: String? = null
    @SerializedName("abv")
    @Expose
    private val abv: Double? = null
    @SerializedName("ibu")
    @Expose
    private val ibu: Double? = null
    @SerializedName("target_fg")
    @Expose
    private val targetFg: Double? = null
    @SerializedName("target_og")
    @Expose
    private val targetOg: Double? = null
    @SerializedName("ebc")
    @Expose
    private val ebc: Double? = null
    @SerializedName("srm")
    @Expose
    private val srm: Double? = null
    @SerializedName("ph")
    @Expose
    private val ph: Double? = null
    @SerializedName("attenuation_level")
    @Expose
    private val attenuationLevel: Double? = null
    @SerializedName("food_pairing")
    @Expose
    private val foodPairing: List<String>? = null
    @SerializedName("brewers_tips")
    @Expose
    private val brewersTips: String? = null
    @SerializedName("contributed_by")
    @Expose
    private val contributedBy: String? = null

}
