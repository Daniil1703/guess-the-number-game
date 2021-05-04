package com.justai.jaicf.game

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class GameApplicationRun

fun main(args: Array<String>) {
    runApplication<GameApplicationRun>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}