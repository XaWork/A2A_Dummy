package com.a2a.app.data.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class CustomBulkOrder : Serializable {

    @SerializedName("name")
    lateinit var firstName: String

    @SerializedName("email")
    lateinit var email: String

    @SerializedName("mobile")
    lateinit var phone: String

    @SerializedName("city")
    lateinit var city: String

    @SerializedName("address")
    lateinit var address: String

    @SerializedName("your-message")
    lateinit var message: String

    fun getKeyValuePair(): HashMap<String, String> {
        val keyValuePairMap = HashMap<String,String>()
        keyValuePairMap["name"] = firstName;
        keyValuePairMap["email"] = email;
        keyValuePairMap["mobile"] = phone;
        keyValuePairMap["city"] = city;
        keyValuePairMap["address"] = address;
        keyValuePairMap["msg"] = message;
        return keyValuePairMap;
    }

}
