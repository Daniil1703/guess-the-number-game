package com.justai.jaicf.game

import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.caila.CailaIntentActivator
import com.justai.jaicf.activator.caila.CailaNLUSettings
import com.justai.jaicf.activator.regex.RegexActivator
import com.justai.jaicf.channel.http.HttpBotChannelServlet
import com.justai.jaicf.channel.jaicp.logging.JaicpConversationLogger
import com.justai.jaicf.channel.telegram.TelegramChannel
import com.justai.jaicf.logging.Slf4jConversationLogger
import com.justai.jaicf.game.scenario.mainScenario
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Configuration

import java.util.*
import javax.servlet.annotation.WebServlet

val accessToken: String = System.getenv("JAICP_API_TOKEN") ?: Properties().run {
    load(CailaNLUSettings::class.java.getResourceAsStream("/jaicp.properties"))
    getProperty("apiToken")
}

private val cailaNLUSettings = CailaNLUSettings(
    accessToken = accessToken
)

val templateBot = BotEngine(
    scenario = mainScenario,
    conversationLoggers = arrayOf(
        JaicpConversationLogger(accessToken),
        Slf4jConversationLogger()
    ),
    activators = arrayOf(
        CailaIntentActivator.Factory(cailaNLUSettings),
        RegexActivator
    )
)

@Configuration
@ServletComponentScan
@SpringBootApplication
class Context {

    @WebServlet("")
    class TelegramController: HttpBotChannelServlet(
        TelegramChannel(templateBot, "1707220049:AAFsKcBdVv-p72HkrZENo0uaQujqgRpkU9A")
    )
}

fun main(args: Array<String>) {
    runApplication<Context>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}