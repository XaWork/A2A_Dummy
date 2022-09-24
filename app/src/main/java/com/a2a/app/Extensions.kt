package com.a2a.app

import com.a2a.app.data.model.OrderUpdateModel
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*


fun String.toDate(format: String = "dd-MM-yy HH:mm", serverDateFormat: String = "yyyy-MM-dd'T'HH:mm:ss"): String {
    val converter = SimpleDateFormat(serverDateFormat, Locale.getDefault())
    converter.timeZone = TimeZone.getTimeZone("+5:30")
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(converter.parse(this)!!)
}


fun String.toDateObject(format: String = "dd-MM-yy HH:mm"): Date {
    val converter = SimpleDateFormat(format, Locale.getDefault())
    return converter.parse(this)!!
}

fun OrderUpdateModel.Result.toSummary() = buildString {
    appendln(note)
    append(createdDate.toDate())
}
