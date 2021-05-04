package com.justai.jaicf.game.spring

import com.justai.jaicf.game.accessToken
import com.justai.jaicf.game.gameNumberBot
import com.justai.jaicf.channel.jaicp.JaicpServlet
import com.justai.jaicf.channel.jaicp.JaicpWebhookConnector
import com.justai.jaicf.channel.jaicp.channels.ChatApiChannel
import com.justai.jaicf.channel.jaicp.channels.ChatWidgetChannel
import com.justai.jaicf.channel.telegram.TelegramChannel
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@ServletComponentScan
@EnableAutoConfiguration
class SpringConfiguration {

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