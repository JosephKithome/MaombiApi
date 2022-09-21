package com.joseph.jblog

import com.joseph.jblog.entity.Role
import com.joseph.jblog.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
//@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])

class JbLogApplication: CommandLineRunner{
	@Autowired
	private val roleRepository: RoleRepository? = null

	@Throws(Exception::class)
	override fun run(vararg args: String) {
		val adminRole = Role()
		adminRole.name = "ROLE_ADMIN"
		roleRepository!!.save(adminRole)
		val userRole = Role()
		userRole.name = "ROLE_USER"
		roleRepository.save(userRole)
	}

}


fun main(args: Array<String>) {
	runApplication<JbLogApplication>(*args)




}
