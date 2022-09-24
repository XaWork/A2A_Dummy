package com.a2a.app.mappers

import com.a2a.app.data.model.*
import com.google.android.gms.common.internal.service.Common

fun CityModel.Result.toCommonModel(): CommonModel {
    return CommonModel(
        id = id,
        file = file,
        name = name,
        description = description,
        slug = slug,
        subHeading = subHeading
    )
}

fun AllCategoryModel.Result.toCommonModel():CommonModel{
    return CommonModel(
        id = id,
        file = file,
        name = name,
        description = description,
        slug = slug,
        subHeading = ""
    )
}

fun HomeModel.Result.Blog.toCommonModel(): CommonModel{
    return CommonModel(
        id = _id,
        file = image,
        name = title,
        description = description,
        slug = slug,
        subHeading =  ""
    )
}

fun ServiceTypeModel.Result.toCommonModel(): CommonModel{
    return CommonModel(
        id = _id,
        file = file,
        name = name,
        description = desc,
        slug = "",
        subHeading = ""
    )
}