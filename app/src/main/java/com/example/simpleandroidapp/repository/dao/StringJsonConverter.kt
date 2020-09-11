package com.example.simpleandroidapp.repository.dao

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.objectbox.converter.PropertyConverter

class StringJsonConverter : PropertyConverter<List<String>, String?> {
        override fun convertToDatabaseValue(entityProperty: List<String>?): String? {
            return if (entityProperty == null) {
                null
            } else {
                Gson().toJson(entityProperty)
            }
        }

        override fun convertToEntityProperty(databaseValue: String?): List<String> {
            return if (databaseValue == null) {
                emptyList()
            } else {
                Gson().fromJson(
                    databaseValue,
                    object : TypeToken<List<String>>() {}.type
                )
            }
        }
}
