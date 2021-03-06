package com.justai.jaicf.game.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.game.GameController
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.getValue

object RobotPlayScenario : Scenario {

    const val playRobot = "/playRobot"

    override val model by Scenario {

        state(playRobot) {
            action {
                val game = GameController(context)
                game.reset()
                game.guessNumber = game.randomNumber()
                reactions.run {
                    say("Я загадал число от 0 до 100!")
                    say("Попытайся отгадать, а я буду говорить больше или меньше.")
                }
            }

            state("game", modal = true, noContext = true){
                activators {
                    intent("numbers")
                }
                action {
                    val game = GameController(context)
                    val getHumanNumber = activator.caila?.slots?.values
                    val humanNumber = getHumanNumber?.first()?.toInt()
                    if (humanNumber == game.guessNumber) {
                        reactions.run {
                            say("Молодец! ты победил! Мое число - $humanNumber")
                            go(exitGame)
                        }
                    }  else if (humanNumber != null) {
                        if (humanNumber > game.guessNumber as Int) {
                            reactions.say("Мое число меньше!")
                        } else if (humanNumber < game.guessNumber as Int) {
                            reactions.say("Мое число больше!")
                        }
                    }
                }
            }

            fallback {
                reactions.run{
                    sayRandom("Назовите число пожалуйста!", "Я не вижу тут числа!", "А число где!")
                }
            }
        }
    }
}
