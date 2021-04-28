package com.justai.jaicf.game

import com.justai.jaicf.game.connections.Context
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

class ServletInitializer : SpringBootServletInitializer() {
	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {

		return application.sources(Context::class.java)
	}

}
