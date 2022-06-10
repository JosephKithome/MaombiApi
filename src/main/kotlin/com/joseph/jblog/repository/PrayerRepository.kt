package com.joseph.jblog.repository

import com.joseph.jblog.entity.Prayer
import org.springframework.data.jpa.repository.JpaRepository

interface PrayerRepository: JpaRepository<Prayer, Long> {
}