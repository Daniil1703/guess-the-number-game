package com.justai.jaicf.game

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GuessTheNumberApplication

fun main(args: Array<String>) {
	runApplication<GuessTheNumberApplication>(*args)
}
