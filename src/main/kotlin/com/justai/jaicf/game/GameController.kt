package com.justai.jaicf.game

import com.justai.jaicf.context.BotContext

class GameController(context: BotContext) {
    var startNumber: Int? by context.session
    var endNumber: Int? by context.session
    var guessNumber: Int? by context.session
    var container: Int? by context.session

    fun reset() {
        startNumber = 0
        endNumber = 100
        guessNumber = 0
        container = 0
    }

    fun randomNumber(): Int {
        return (startNumber as Int..endNumber as Int).random()
    }

    fun checkWinner(): Boolean {
        if (startNumber == endNumber || endNumber as Int - startNumber as Int == 1) {
            return true
        }
        return false
    }
}