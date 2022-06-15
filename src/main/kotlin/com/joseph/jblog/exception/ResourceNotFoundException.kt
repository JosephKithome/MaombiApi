package com.joseph.jblog.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException(
    resourceName: String?,
    fieldName: String?,
    fieldValue: Long
        ) : RuntimeException(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue))