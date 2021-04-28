package com.justai.jaicf.game.connections

import com.justai.jaicf.channel.jaicp.JaicpServlet
import com.justai.jaicf.channel.jaicp.JaicpWebhookConnector
import com.justai.jaicf.channel.jaicp.channels.ChatApiChannel
import com.justai.jaicf.channel.jaicp.channels.ChatWidgetChannel
import com.justai.jaicf.channel.telegram.TelegramChannel
import com.justai.jaicf.game.accessToken
import com.justai.jaicf.game.gameNumberBot
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ServletComponentScan
@SpringBootApplication
class Context {

    @Bean
    fun jaicpServlet() = ServletRegistrationBean(
        JaicpServlet(
            JaicpWebhookConnector(
                botApi = gameNumberBot,
                accessToken = accessToken,
                channels = listOf(
                    ChatWidgetChannel,
                    TelegramChannel,
                    ChatApiChannel
                )
            )
        ),
        "/"
    ).apply {
        setLoadOnStartup(1)
    }
}

fun main(args: Array<String>) {
    runApplication<Context>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}