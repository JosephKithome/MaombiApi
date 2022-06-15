package com.joseph.jblog

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])

class JbLogApplication

@Autowired lateinit var modelMapper: ModelMapper

fun main(args: Array<String>) {
	runApplication<JbLogApplication>(*args)
}
