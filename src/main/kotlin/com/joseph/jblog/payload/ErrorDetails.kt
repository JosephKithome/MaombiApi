package com.joseph.jblog.payload

import lombok.Data
import java.time.LocalDate

@Data
class ErrorDetails {
    var date: LocalDate? = LocalDate.now()
    var message: String? = null
    var details: String? = null

    constructor(date: LocalDate, message: String?, details: String) {}
}