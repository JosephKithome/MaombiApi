package com.joseph.jblog.utils

import org.apache.tomcat.util.ExceptionUtils
import org.slf4j.LoggerFactory

class LogHelper {

    companion object {

        private var log = LoggerFactory.getLogger(LogHelper::class.java.name)
        fun debug(msg: String) {
            log.debug("${getLogInfo()}||$msg")
        }

        fun info(msg: String) {
            //log.info("Logger called from ${this.getCaller()}")
            log.info("${getLogInfo()}||$msg")
        }

        fun warn(msg: String) {
            //log.info("Logger called from ${this.getCaller()}")
            log.warn("${getLogInfo()}||$msg")
        }

        fun error(msg:String){
            log.error("${getLogInfo()}||$msg")
        }

//        fun debugTrace(e: Exception) {
//            log.error("${getLogInfo()}||${ExceptionUtils.getFullStackTrace(e)}")
//        }

        fun getLogInfo(): String {
            try {
                val thisCaller = Thread.currentThread().stackTrace[3]
                return "${thisCaller.className}.${thisCaller.methodName}"
            } catch (e: java.lang.Exception) {
                log.warn("Error finding log, defaulting!")
            }
            return LogHelper::class.java.name
        }
    }
}

