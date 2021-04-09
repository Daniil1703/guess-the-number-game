package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.getValue

object RobotPlayScenario : Scenario {

    const val playRobot = "/playRobot"

    override val scenario by Scenario {

        state(playRobot) {
            action {
                context.session["randomNumber"] = getRandomNumber()
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
                    val randomNumber = context.session["randomNumber"].toString().toInt()
                    val getHumanNumber = activator.caila?.slots?.values
                    val humanNumber = getHumanNumber?.first()?.toInt()
                    if (humanNumber == randomNumber) {
                        reactions.run {
                            say("Молодец! ты победил! Мое число - $humanNumber")
                            go(exitGame)
                        }
                    }  else if (humanNumber != null) {
                        if (humanNumber > randomNumber) {
                            reactions.say("Мое число меньше!")
                        } else if (humanNumber < randomNumber) {
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

fun getRandomNumber(): Int {
    return (0..100).random()
}
