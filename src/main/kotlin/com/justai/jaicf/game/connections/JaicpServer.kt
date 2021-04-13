package com.justai.jaicf.game.connections

import com.justai.jaicf.channel.jaicp.JaicpServer
import com.justai.jaicf.channel.jaicp.channels.ChatApiChannel
import com.justai.jaicf.channel.jaicp.channels.ChatWidgetChannel
import com.justai.jaicf.channel.jaicp.channels.TelephonyChannel
import com.justai.jaicf.game.accessToken
import com.justai.jaicf.game.templateBot

fun main() {
    JaicpServer(
        botApi = templateBot,
        accessToken = accessToken,
        channels = listOf(
            ChatApiChannel,
            ChatWidgetChannel,
            TelephonyChannel
        )
    ).start(wait = true)
}