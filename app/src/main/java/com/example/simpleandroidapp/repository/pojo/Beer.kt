package com.example.simpleandroidapp.repository.pojo

import android.os.Parcelable
import com.example.simpleandroidapp.repository.dao.StringJsonConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class Beer : Parcelable {
    @Id(assignable = true)
    @SerializedName("id")
    @Expose
    var id: Long? = null
    @SerializedName("name")
    @Expose
    val name: String? = null
    @SerializedName("tagline")
    @Expose
    val tagline: String? = null
    @SerializedName("first_brewed")
    @Expose
    val firstBrewed: String? = null
    @SerializedName("description")
    @Expose
    val description: String? = null
    @SerializedName("image_url")
    @Expose
    val imageUrl: String? = null
    @SerializedName("abv")
    @Expose
    val abv: Double? = null
    @SerializedName("ibu")
    @Expose
    val ibu: Double? = null
    @SerializedName("target_fg")
    @Expose
    val targetFg: Double? = null
    @SerializedName("target_og")
    @Expose
    val targetOg: Double? = null
    @SerializedName("ebc")
    @Expose
    val ebc: Double? = null
    @SerializedName("srm")
    @Expose
    val srm: Double? = null
    @SerializedName("ph")
    @Expose
    val ph: Double? = null
    @SerializedName("attenuation_level")
    @Expose
    val attenuationLevel: Double? = null
    // ObjectBox does not support List<String>. It assumes you mean ToMany<String>.
    // You need to define a converter for List<String>
    @Convert(converter = StringJsonConverter::class, dbType = String::class)
    @SerializedName("food_pairing")
    @Expose
    val foodPairing: List<String>? = null

    @SerializedName("brewers_tips")
    @Expose
    val brewersTips: String? = null
    @SerializedName("contributed_by")
    @Expose
    val contributedBy: String? = null

    fun setId(id: Long) {
        this.id = id
    }
}
