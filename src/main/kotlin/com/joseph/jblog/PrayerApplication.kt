package com.joseph.jblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])

class JbLogApplication


fun main(args: Array<String>) {
	runApplication<JbLogApplication>(*args)
}
