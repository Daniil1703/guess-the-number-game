package com.justai.jaicf.game.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.getValue
import com.justai.jaicf.reactions.buttons

object HumanPlayScenario : Scenario {

    const val playHuman = "/playHuman"
    private var startNumber = 0
    private var endNumber = 100
    private var guessNumber = 0
    private var container = 0

    override val model by Scenario {

        state(playHuman) {
            action {
                reactions.run {
                    say("Отлично! Ты загадываешь число, я пытаюсь его угадать!")
                    say("Загадал?")
                    buttons("Да" to "game")
                }
            }

            state("game") {
                action {
                    if (startNumber == endNumber || endNumber - startNumber == 1) {
                        guessNumber = tryGuessNumber(startNumber, endNumber)
                        reactions.go("winner")
                    }
                    guessNumber = tryGuessNumber(startNumber, endNumber)
                    reactions.say("Ваше число - $guessNumber ?")
                    container = guessNumber
                }

                state("less") {
                    activators {
                        intent("less")
                    }

                    action {
                        endNumber = container
                        reactions.go("../")
                    }
                }

                state("more") {
                    activators {
                        intent("more")
                    }
                        action {
                            startNumber = container
                            reactions.go("../")
                        }
                }

                state("no") {
                    activators {
                        intent( "notMyNumber")
                    }
                    action {
                        reactions.say("Хорошо, тогда скажите, больше или меньше? Я сейчас угадаю.")
                    }
                }

                state("yes") {
                    activators {
                        intent("myNumber")
                    }

                    action {
                        reactions.run{
                            say("Ура! Я победил! Не так уж и тяжело было угадать число $guessNumber :)")
                            startNumber = 0
                            endNumber = 100
                            go(exitGame)
                        }
                    }
                }

                state("winner") {
                    action {
                        reactions.say("Я победил! Вы загадали число - $guessNumber и не надо говорить, что это не так!")
                        startNumber = 0
                        endNumber = 100
                        reactions.go(exitGame)
                    }
                }

                fallback {
                    reactions.run {
                        say("Пожалуйста, просто скажите, ваше число больше или меньше?")
                    }
                }
            }
        }
    }
}

fun tryGuessNumber(startNumber: Int, endNumber: Int): Int {
    return (startNumber..endNumber).random()
}