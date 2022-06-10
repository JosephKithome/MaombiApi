package com.joseph.jblog.utils

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class RestResponse(
    body: ResponseObject,
    status: HttpStatus
): ResponseEntity<ResponseObject>(body, status) {
}