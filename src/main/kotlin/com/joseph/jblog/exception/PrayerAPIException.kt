package com.joseph.jblog.exception

import org.springframework.http.HttpStatus

class PrayerAPIException : RuntimeException {
    var status: HttpStatus
        private set
    override var message: String
        private set

    constructor(status: HttpStatus, message: String) {
        this.status = status
        this.message = message
    }

    constructor(message: String?, status: HttpStatus, message1: String) : super(message) {
        this.status = status
        this.message = message1
    }
}