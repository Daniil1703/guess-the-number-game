package com.justai.jaicf.game.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.game.GameController
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.getValue
import com.justai.jaicf.reactions.buttons

object HumanPlayScenario : Scenario {

    const val playHuman = "/playHuman"

    override val model by Scenario {

        state(playHuman) {
            action {
                reactions.run {
                    val game = GameController(context)
                    game.reset()
                    say("Отлично! Ты загадываешь число, я пытаюсь его угадать!")
                    say("Загадал?")
                    buttons("Да" to "game")
                }
            }

            state("game") {
                action {
                    val game = GameController(context)
                    if (game.checkWinner()) {
                        game.guessNumber = game.randomNumber()
                        reactions.say("Я победил! Вы загадали число - ${game.guessNumber}!")
                        game.reset()
                        reactions.go(exitGame)
                    } else {
                        game.guessNumber = game.randomNumber()
                        reactions.say("Ваше число - ${game.guessNumber} ?")
                        game.container = game.guessNumber
                    }
                }

                state("less") {
                    activators {
                        intent("less")
                    }

                    action {
                        val game = GameController(context)
                        game.endNumber = game.container
                        reactions.go("../")
                    }
                }

                state("more") {
                    activators {
                        intent("more")
                    }
                        action {
                            val game = GameController(context)
                            game.startNumber = game.container
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
                            val game = GameController(context)
                            say("Ура! Я победил! Не так уж и тяжело было угадать число ${game.guessNumber} :)")
                            game.reset()
                            go(exitGame)
                        }
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